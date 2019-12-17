package minecraftschurli.arsmagicalegacy.lore;

import minecraftschurli.arsmagicalegacy.ArsMagicaLegacy;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.StringNBT;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Story{
	private final String resourceName;
	private short parts;
	private String title;
	private String author;
	private int[] colors;

	private final List<List<StringNBT>> partPages;

	public int getStoryPassColor(int pass){
		if (colors.length <= pass){
			return colors[colors.length - 1];
		}
		return colors[pass];
	}

	public short getNumParts(){
		return parts;
	}

	public List<StringNBT> getStoryPart(int index){
		if (index < 0 || index >= parts){
			return new ArrayList<>();
		}
		return partPages.get(index);
	}

	public String getTitle(){
		return this.title;
	}

	public String getAuthor(){
		return this.author;
	}

	public Story(String resourceName) throws IOException{
		this.resourceName = resourceName;
		partPages = new ArrayList<>();
		parseFile();

		if (title == null || author == null){
			throw new IOException("Invalid file - needs #TITLE and #AUTHOR directives at the beginning!");
		}
	}

	private void parseFile(){
		String fileText = readResource();
		String[] split = fileText.split("<<__>>");
		parts = (short)split.length;
		for (String s : split){
			if (s.equals("") || s.equals("\n")) continue;
			if (s.startsWith("\n")) s = s.replaceFirst("\n", "");
			s = s.replace("\r\n", "\n");
			partPages.add(splitStoryPartIntoPages(s));
		}
	}

	private InputStream getResourceAsStream(String resourceName){
		return ArsMagicaLegacy.class.getResourceAsStream(resourceName);
	}

	private String readResource(){
		InputStream stream = getResourceAsStream(resourceName);
		if (stream == null){
			ArsMagicaLegacy.LOGGER.info("Missing Resource '" + resourceName + "'");
			return "";
		}

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try{
			br = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
			String line;
			while ((line = br.readLine()) != null){
				if (line.startsWith("#TITLE")){
					this.title = line.replace("#TITLE", "").trim();
					continue;
				}else if (line.startsWith("#AUTHOR")){
					this.author = line.replace("#AUTHOR", "").trim();
					continue;
				}else if (line.startsWith("#COLORS")){
					String colorString = line.replace("#COLORS", "").trim();
					String[] colorStrings = colorString.split(" ");
					this.colors = new int[4];
					for (int i = 0; i < 4; ++i){
						if (i < colorStrings.length){
							try{
								this.colors[i] = Integer.parseInt(colorStrings[i], 16);
							}catch (Throwable t){
								this.colors[i] = 0xFFFFFF;
							}
						}else{
							this.colors[i] = 0xFFFFFF;
						}
					}
					continue;
				}
				sb.append(line + "\n");
			}
			br.close();
			stream.close();
		}catch (Throwable t){
			ArsMagicaLegacy.LOGGER.error("Error reading JRN File Data!");
			return "";
		}

		if (this.colors == null){
			this.colors = new int[]{0xFFFFFF, 0xFFFFFF, 0xFFFFFF, 0xFFFFFF};
		}

		return sb.toString();
	}

	public void writePartToNBT(CompoundNBT compound, int part){
		List<StringNBT> storyData = getStoryPart(part);
		if (storyData == null) return;

		//part
		compound.putInt("story_part", part);

		//title
		StringNBT title = StringNBT.func_229705_a_(this.title);
		compound.put("title", title);

		//author
		StringNBT author = StringNBT.func_229705_a_(this.author);
		compound.put("author", author);

		//pages
		ListNBT pages = new ListNBT();
		pages.addAll(storyData);
		compound.put("pages", pages);
	}

	public static void writePartToNBT(CompoundNBT compound, List<StringNBT> storyData){
		//pages
		ListNBT pages = new ListNBT();
		for (StringNBT page : storyData){
			StringNBT newPage = StringNBT.func_229705_a_("{\"text\":\"" + page.getString() + "\"}");
			pages.add(newPage);
		}
		compound.put("pages", pages);
	}

	public static ItemStack finalizeStory(ItemStack stack, String title, String author){
		if (stack.getTag() == null) return stack;
		stack.getTag().put("title", StringNBT.func_229705_a_(title));
		stack.getTag().put("author", StringNBT.func_229705_a_(author));
		//stack = InventoryUtilities.replaceItem(stack, Items.WRITTEN_BOOK);
		return stack;
	}

	public void writeMultiplePartsToNBT(CompoundNBT compound, List<Short> parts){
		//title
		StringNBT title = StringNBT.func_229705_a_(this.title);
		compound.put("title", title);

		//author
		StringNBT author = StringNBT.func_229705_a_(this.author);
		compound.put("author", author);

		Collections.sort(parts);

		ListNBT pages = new ListNBT();
		for (Short i : parts){
			List<StringNBT> storyData = getStoryPart(i);
			if (storyData.equals("")) continue;
			for (StringNBT page : storyData){
				pages.add(page);
			}
		}
		compound.put("pages", pages);
	}

	public static List<StringNBT> splitStoryPartIntoPages(String storyPart){
		ArrayList<StringNBT> parts = new ArrayList<>();
		String[] words = storyPart.split(" ");
		String currentPage = "";


		for (String word : words){
			//special commands
			if (word.contains("<newpage>")){
				int idx = word.indexOf("<newpage>");
				String preNewPage = word.substring(0, idx);
				String postNewPage = word.substring(idx + "<newpage>".length());
				while (preNewPage.endsWith("\n")) preNewPage = preNewPage.substring(0, preNewPage.lastIndexOf('\n'));
				if (getStringOverallLength(currentPage + preNewPage) > 256){
					parts.add(StringNBT.func_229705_a_(currentPage));
					currentPage = preNewPage.trim();
				}else{
					currentPage += " " + preNewPage.trim();
				}
				parts.add(StringNBT.func_229705_a_(currentPage));
				while (postNewPage.startsWith("\n")) postNewPage = postNewPage.replaceFirst("\n", "");
				currentPage = postNewPage.trim();
				continue;
			}

			//length limit
			if (getStringOverallLength(currentPage + word) > 256){
				parts.add(StringNBT.func_229705_a_(currentPage));
				currentPage = word;
				if (getStringOverallLength(currentPage) > 256){
					currentPage = currentPage.substring(0, getStringSplitIndex(currentPage, 255));
					parts.add(StringNBT.func_229705_a_(currentPage));
					currentPage = "";
				}
				continue;
			}
			//
			if (currentPage.equals("")){
				currentPage = word.trim();
			}else{
				currentPage += " " + word;
			}
		}
		parts.add(StringNBT.func_229705_a_(currentPage));

		return parts;
	}

	private static int getStringOverallLength(String s){
		int length = 0;
		for (int i = 0; i < s.length(); ++i){
			char c = s.charAt(i);
			if (c == '\n'){
				length += length % 19;
			}else{
				length++;
			}
		}
		return length;
	}

	private static int getStringSplitIndex(String s, int splitpoint){
		int length = 0;
		int index = 0;
		for (int i = 0; i < s.length(); ++i){
			char c = s.charAt(i);
			if (c == '\n'){
				length += length % 19;
			}else{
				length++;
			}
			if (length > splitpoint){
				return index;
			}else{
				index++;
			}
		}
		return index - 1;
	}
}
