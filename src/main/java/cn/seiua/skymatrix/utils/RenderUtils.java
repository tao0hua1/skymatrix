package cn.seiua.skymatrix.utils;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.Box;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL11C;

import java.awt.*;

public class RenderUtils {

    public static boolean outline = false;
    private static Color color;
    private static boolean cent;

    public static void drawSolidBox(Box bb, MatrixStack matrixStack) {
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);

        bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
                VertexFormats.POSITION);
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.minZ)
                .next();
        tessellator.draw();
    }

    public static void setColor(Color col) {
        color = col;
    }

    public static void setupColor() {
        float rr = color.getRed() / 255f;
        float g = color.getGreen() / 255f;
        float b = color.getBlue() / 255f;
        float a = color.getAlpha() / 255f;
        RenderSystem.setShaderColor(rr, g, b, a);
    }

    public static void cent() {
        cent = true;
    }

    public static void resetCent() {
        cent = false;
    }

    public static void resetColor() {

        RenderSystem.setShaderColor(1, 1, 1, 1);
    }

    public static Box getCenterBox(Box bb) {

        double wdx = (bb.maxX - bb.minX) / 2;
        double wdy = (bb.maxY - bb.minY) / 2;
        double wdz = (bb.maxZ - bb.minZ) / 2;


        return new Box(bb.minX - wdx, bb.minY - wdy, bb.minZ - wdz, bb.minX + wdx, bb.minY + wdy, bb.minZ + wdz);

    }

    public static void drawRound2D(Box bb, MatrixStack matrixStack, int r) {

        if (cent) bb = getCenterBox(bb);
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        GL11C.glEnable(GL11C.GL_LINE_SMOOTH);
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
                VertexFormats.POSITION);
        setupColor();
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);


        bufferBuilder.vertex(matrix, (float) bb.minX, (float) bb.minY + r, (float) bb.minZ).next();
        bufferBuilder.vertex(matrix, (float) bb.minX, (float) bb.maxY - r, (float) bb.minZ).next();
        bufferBuilder.vertex(matrix, (float) bb.maxX, (float) bb.maxY - r, (float) bb.minZ).next();
        bufferBuilder.vertex(matrix, (float) bb.maxX, (float) bb.minY + r, (float) bb.minZ).next();

        bufferBuilder.vertex(matrix, (float) bb.minX + r, (float) bb.minY, (float) bb.minZ).next();
        bufferBuilder.vertex(matrix, (float) bb.minX + r, (float) bb.maxY, (float) bb.minZ).next();
        bufferBuilder.vertex(matrix, (float) bb.maxX - r, (float) bb.maxY, (float) bb.minZ).next();
        bufferBuilder.vertex(matrix, (float) bb.maxX - r, (float) bb.minY, (float) bb.minZ).next();


        tessellator.draw();
        if (r > 0) {


            bufferBuilder = tessellator.getBuffer();
            bufferBuilder.begin(VertexFormat.DrawMode.TRIANGLES,
                    VertexFormats.POSITION);
            int spx = (int) (bb.maxX - r);
            int spy = (int) (bb.minY + r);
            float lx = 0;
            float ly = 0;
            boolean nmsl = true;
            for (int i = 0; i <= 360; i++) {
                if (i == 90) {
                    spx = (int) (bb.minX + r);
                    spy = (int) (bb.minY + r);
                    nmsl = false;
                }
                if (i == 180) {
                    spx = (int) (bb.minX + r);
                    spy = (int) (bb.maxY - r);
                    nmsl = true;
                }
                if (i == 270) {
                    spx = (int) (bb.maxX - r);
                    spy = (int) (bb.maxY - r);
                    nmsl = false;
                }
                ly = r * (float) Math.sin(Math.toRadians(i));
                lx = r * (float) Math.cos(Math.toRadians(i));
                bufferBuilder.vertex(matrix, (float) spx, (float) spy, (float) bb.minZ).next();
                bufferBuilder.vertex(matrix, (float) spx + lx, (float) spy - ly, (float) bb.minZ).next();
                bufferBuilder.vertex(matrix, nmsl ? (float) spx + ly : (float) spx - ly, nmsl ? (float) spy - lx : (float) spy + lx, (float) bb.minZ).next();

            }
            tessellator.draw();
        }
        GlStateManager._disableBlend();
        resetColor();
    }

    private static void line(BufferBuilder bufferBuilder, int r, Box bb, Matrix4f matrix, int s, int e, int sx, int sy) {
        float lx = r * (float) Math.sin(Math.toRadians(s));
        float ly = r * (float) Math.cos(Math.toRadians(s));
        for (int i = s; i <= e; i++) {
            bufferBuilder
                    .vertex(matrix, sx + lx, sy + ly, (float) bb.minZ)
                    .next();
            lx = r * (float) Math.sin(Math.toRadians(i));
            ly = r * (float) Math.cos(Math.toRadians(i));
            bufferBuilder
                    .vertex(matrix, sx + lx, sy + ly, (float) bb.minZ)
                    .next();
        }
    }

    public static void drawOutlineBox(Box bb, MatrixStack matrixStack) {
        Matrix4f matrix = matrixStack.peek().getPositionMatrix();
        Tessellator tessellator = RenderSystem.renderThreadTesselator();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        RenderSystem.setShader(GameRenderer::getPositionProgram);
        GL11C.glEnable(GL11C.GL_LINE_SMOOTH);
        bufferBuilder.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP,
                VertexFormats.POSITION);
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.minZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.maxX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.maxZ)
                .next();

        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.minZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.minY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.maxZ)
                .next();
        bufferBuilder
                .vertex(matrix, (float) bb.minX, (float) bb.maxY, (float) bb.minZ)
                .next();
        tessellator.draw();
    }

    public static void drawRound2DOutline(Box box, MatrixStack matrices, int i) {
        outline = true;
        drawRound2D(box, matrices, i);
        outline = false;
    }

    public static void drawColorRound2D(Box bb, MatrixStack matrixStack, int i) {
        if (cent) bb = getCenterBox(bb);

        int t = (int) (bb.maxX - bb.minX);

        Color color1 = Color.red;
        float th = 0;

        for (int j = 0; j < t; j++) {
            th = j * 1.0f / t;
            color1 = ColorUtils.setHue(color1, th);
            setColor(color1);
            Matrix4f matrix = matrixStack.peek().getPositionMatrix();
            Tessellator tessellator = RenderSystem.renderThreadTesselator();
            BufferBuilder bufferBuilder = tessellator.getBuffer();
            RenderSystem.setShader(GameRenderer::getPositionProgram);
            bufferBuilder.begin(VertexFormat.DrawMode.QUADS,
                    VertexFormats.POSITION);
            setupColor();
            GlStateManager._enableBlend();
            GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            bufferBuilder.vertex(matrix, (float) bb.minX + j, (float) bb.minY, (float) bb.minZ).next();
            bufferBuilder.vertex(matrix, (float) bb.minX + j, (float) bb.maxY, (float) bb.minZ).next();
            bufferBuilder.vertex(matrix, (float) bb.minX + j + 1, (float) bb.maxY, (float) bb.minZ).next();
            bufferBuilder.vertex(matrix, (float) bb.minX + j + 1, (float) bb.minY, (float) bb.minZ).next();
            tessellator.draw();
        }

        GlStateManager._disableBlend();
        resetColor();


    }
}
