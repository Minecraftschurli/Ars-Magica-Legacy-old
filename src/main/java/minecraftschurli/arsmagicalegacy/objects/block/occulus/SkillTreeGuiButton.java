package minecraftschurli.arsmagicalegacy.objects.block.occulus;

import minecraftschurli.arsmagicalegacy.*;
import minecraftschurli.arsmagicalegacy.api.skill.*;
import minecraftschurli.arsmagicalegacy.util.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraftforge.fml.client.config.*;

public class SkillTreeGuiButton extends GuiButtonExt {

    public final int id;
    public final int page;
    SkillTree tree;
    boolean inverted;

    public SkillTreeGuiButton(int tabId, int x, int y, SkillTree tree, int page, boolean inverted, IPressable handler) {
        super(x, y, 22, 22, "", handler);
        this.tree = tree;
        this.inverted = inverted;
        this.page = page;
        this.id = tabId;
    }

    @Override
    public void renderButton(int mouseX, int mouseY, float partial) {
        if (visible) {
            Minecraft.getInstance().getTextureManager().bindTexture(new ResourceLocation(ArsMagicaLegacy.MODID, "textures/gui/occulus/overlay.png"));
            float f = 0.00390625F;
            if (inverted)
                RenderUtils.drawBox(x, y, 22, 22, blitOffset, 22 * f, 210 * f + 22 * f, 0, 210 * f);
            else
                RenderUtils.drawBox(x, y, 22, 22, blitOffset, 0, 210 * f, 22 * f, 210 * f + 22 * f);
            //blit(x, y, 0, 210, 22, 22);
            Minecraft.getInstance().getTextureManager().bindTexture(tree.getIcon());
            RenderUtils.drawBox(x + 2F, y + 2F, 18, 18, blitOffset, 0, 0, 1, 1);
        }
    }

    public SkillTree getTree() {
        return tree;
    }
}
