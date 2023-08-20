package cn.seiua.skymatrix.client.waypoint;

import cn.seiua.skymatrix.SkyMatrix;
import cn.seiua.skymatrix.client.Client;
import cn.seiua.skymatrix.client.HypixelWay;
import cn.seiua.skymatrix.gui.ClickGui;
import cn.seiua.skymatrix.gui.Icons;
import cn.seiua.skymatrix.gui.Theme;
import cn.seiua.skymatrix.gui.ui.UI;
import cn.seiua.skymatrix.utils.RenderUtils;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;

public class UIWaypoint extends UI {
    private WaypointGroupEntity entity;

    public UIWaypoint(WaypointGroupEntity entity) {
        this.entity = entity;
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        setHeight(65);
        setWidth(1000);
        setMid(true);
        setMouse(mouseX, mouseY);
        RenderUtils.cent();
        RenderUtils.setColor(isInBox() ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().UNSELECTED.geColor());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 1, getWidth() + 4 + getX(), getY() + getHeight() + 4, 1), context.getMatrices(), 2);
        RenderUtils.setColor(Theme.getInstance().BOARD.geColor());
        RenderUtils.drawRound2D(new Box(getX(), getY(), 1, getWidth() + getX(), getY() + getHeight(), 1), context.getMatrices(), 2);
        ClickGui.iconfontRenderer26.centeredV();
        ClickGui.iconfontRenderer26.centeredH();
        ClickGui.iconfontRenderer26.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.iconfontRenderer26.drawString(context.getMatrices(), getX() - 470, getY(), 2, Icons.LOCATION);

        ClickGui.fontRenderer20.resetCenteredV();
        ClickGui.fontRenderer20.centeredH();
        ClickGui.fontRenderer20.setColor(Theme.getInstance().THEME.geColor());
        ClickGui.fontRenderer20.drawString(context.getMatrices(), getX() - 440, getY(), 2, entity.getName());
        ClickGui.fontRenderer20.drawString(context.getMatrices(), getX() + 50, getY(), 2, "-" + entity.getWorld());


        boolean flag1 = new Vec3d(mouseX, mouseY, 0).subtract(new Vec3d(getX() + 470, getY(), 0)).length() < 15;
        boolean flag2 = new Vec3d(mouseX, mouseY, 0).subtract(new Vec3d(getX() + 435, getY(), 0)).length() < 15;
        boolean flag3 = new Vec3d(mouseX, mouseY, 0).subtract(new Vec3d(getX() + 400, getY(), 0)).length() < 15;


        ClickGui.iconfontRenderer26.centeredV();
        ClickGui.iconfontRenderer26.centeredH();
        ClickGui.iconfontRenderer26.setColor(flag1 ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().THEME.geColor());
        ClickGui.iconfontRenderer26.drawString(context.getMatrices(), getX() + 470, getY(), 2, entity.show ? Icons.EYE : Icons.EYE_BLOCKED);
        ClickGui.iconfontRenderer26.setColor(flag2 ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().THEME.geColor());
        ClickGui.iconfontRenderer26.drawString(context.getMatrices(), getX() + 435, getY(), 2, Icons.EDIT);
        ClickGui.iconfontRenderer30.centeredV();
        ClickGui.iconfontRenderer30.centeredH();
        ClickGui.iconfontRenderer30.setColor(flag3 ? Theme.getInstance().THEME_UI_SELECTED.geColor() : Theme.getInstance().THEME.geColor());
        ClickGui.iconfontRenderer30.drawString(context.getMatrices(), getX() + 400, getY(), 2, Icons.REMOVE);

    }

    @Override
    public void initUI() {

    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean flag1 = new Vec3d(mouseX, mouseY, 0).subtract(new Vec3d(getX() + 470, getY(), 0)).length() < 15;
        boolean flag2 = new Vec3d(mouseX, mouseY, 0).subtract(new Vec3d(getX() + 435, getY(), 0)).length() < 15;
        boolean flag3 = new Vec3d(mouseX, mouseY, 0).subtract(new Vec3d(getX() + 400, getY(), 0)).length() < 15;

        if (button == 0) {
            if (flag3) {
                this.entity.remove = true;
            }
            if (flag1) {
                this.entity.show = !this.entity.show;
            }
            if (flag2) {
                if (this.entity.world.equals(HypixelWay.getInstance().way())) {
                    Waypoint.getInstance().name = this.entity.name;
                    Waypoint.getInstance().status = Waypoint.Status.EDIT;
                    SkyMatrix.mc.setScreen(null);
                } else {
                    Client.sendSimpleMessage(Text.translatable("client.warning.waypoint.illegal-world"));
                }
            }
        }
        return false;
    }
}
