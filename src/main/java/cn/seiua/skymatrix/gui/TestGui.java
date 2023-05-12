package cn.seiua.skymatrix.gui;

import cn.seiua.skymatrix.client.Client;
import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Config;
import cn.seiua.skymatrix.client.component.Use;
import cn.seiua.skymatrix.config.Value;
import cn.seiua.skymatrix.config.option.KeyBind;
import cn.seiua.skymatrix.font.FontRenderer;
import cn.seiua.skymatrix.font.FontUtils;
import cn.seiua.skymatrix.utils.GlUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.awt.*;
import java.util.Arrays;


@Component
@Config(name = "clickgui")
public class TestGui extends Screen {

    @Use
    public Client client;

    @Value(name = "KEYBIND")
    public KeyBind bind=new KeyBind("KEYBIND", Arrays.asList(GLFW.GLFW_KEY_LEFT_SHIFT), this::open);

    FontRenderer fontRenderer;
    public TestGui() {
        super(Text.empty());
    }

    @Override
    protected void init() {
        fontRenderer= FontUtils.getFontRenderer("fz.ttf", Font.BOLD);

        super.init();
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {

        fontRenderer.setColor(new Color(0, 255, 194,255));
        fontRenderer.setDrawSize(23);
        fontRenderer.centeredV();
        fontRenderer.centeredH();
        fontRenderer.drawStringWithOutline(matrices,mouseX,mouseY,"123456778");
        super.render(matrices, mouseX, mouseY, delta);
    }

    public void open(){
        client.openGui(TestGui.class);
    }


}
