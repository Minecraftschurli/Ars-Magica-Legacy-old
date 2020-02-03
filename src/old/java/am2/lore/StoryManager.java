package am2.lore;

import am2.*;

import java.io.*;
import java.util.*;

public class StoryManager{
	private ArrayList<Story> stories;
	private static final Random rand = new Random();

	public static StoryManager INSTANCE = new StoryManager();

	private StoryManager(){
		stories = new ArrayList<Story>();
	}

	public void AddStory(String resourceFileName) throws IOException{
		try{
			stories.add(new Story(resourceFileName));
		}catch (Exception ex){
			LogHelper.info(ex.getMessage());
		}
	}

	public Story getRandomStory(){
		return stories.get(rand.nextInt(stories.size()));
	}

	public int getRandomPart(Story story){
		return rand.nextInt(story.getNumParts());
	}

	public ArrayList<Story> allStories(){
		return stories;
	}

	public Story getByTitle(String title){
		for (Story s : stories){
			if (s.getTitle().equals(title)) return s;
		}
		return null;
	}
}
