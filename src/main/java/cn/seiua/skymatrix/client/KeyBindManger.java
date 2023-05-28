package cn.seiua.skymatrix.client;

import cn.seiua.skymatrix.client.component.Component;
import cn.seiua.skymatrix.client.component.Event;
import cn.seiua.skymatrix.client.component.Init;
import cn.seiua.skymatrix.client.component.Use;
import cn.seiua.skymatrix.config.option.KeyBind;
import cn.seiua.skymatrix.event.EventTarget;
import cn.seiua.skymatrix.event.events.KeyboardEvent;
import cn.seiua.skymatrix.event.events.MouseEvent;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Event(register = true)
public class KeyBindManger /*implements Runnable*/ {

    @Use
    private ConfigManager configManager;
    private static final Logger logger = LoggerFactory.getLogger("KeyBindManger");
    private List<KeyBind> keyBinds0;
    private List<KeyBind> keyBinds1;
    private List<KeyBind> keyBinds2;

    public static final int MOUSE = 10000;


    public static final HashMap<Integer, String> KEY_MAP = new HashMap<Integer, String>() {{
        put(256, "ESCAPE");
        put(257, "ENTER");
        put(258, "TAB");
        put(259, "BACKSPACE");
        put(260, "INSERT");
        put(261, "DELETE");
        put(262, "RIGHT");
        put(263, "LEFT");
        put(264, "DOWN");
        put(265, "UP");
        put(266, "PAGE UP");
        put(267, "PAGE DOWN");
        put(268, "HOME");
        put(269, "END");
        put(280, "CAPS LOCK");
        put(281, "SCROLL LOCK");
        put(282, "NUM LOCK");
        put(283, "PRINT SCREEN");
        put(284, "PAUSE");
        put(290, "F1");
        put(291, "F2");
        put(292, "F3");
        put(293, "F4");
        put(294, "F5");
        put(295, "F6");
        put(296, "F7");
        put(297, "F8");
        put(298, "F9");
        put(299, "F10");
        put(300, "F11");
        put(301, "F12");
        put(302, "F13");
        put(303, "F14");
        put(304, "F15");
        put(305, "F16");
        put(306, "F17");
        put(307, "F18");
        put(308, "F19");
        put(309, "F20");
        put(310, "F21");
        put(311, "F22");
        put(312, "F23");
        put(313, "F24");
        put(314, "F25");
        put(320, "KP 0");
        put(321, "KP 1");
        put(322, "KP 2");
        put(323, "KP 3");
        put(324, "KP 4");
        put(325, "KP 5");
        put(326, "KP 6");
        put(327, "KP 7");
        put(328, "KP 8");
        put(329, "KP 9");
        put(330, "KP DECIMAL");
        put(331, "KP DIVIDE");
        put(332, "KP MULTIPLY");
        put(333, "KP SUBTRACT");
        put(334, "KP ADD");
        put(335, "KP ENTER");
        put(336, "KP EQUAL");
        put(340, "LEFT SHIFT");
        put(341, "LEFT CONTROL");
        put(342, "LEFT ALT");
        put(343, "LEFT SUPER");
        put(344, "RIGHT SHIFT");
        put(345, "RIGHT CONTROL");
        put(346, "RIGHT ALT");
        put(347, "RIGHT SUPER");
        put(348, "MENU");
    }};
    private CopyOnWriteArrayList<KeyBind> active;

    // key KeyBind
    // key key KeyBind
    // key KeyBind

    private int key0 = -1;
    private int key1 = -1;
    private int key2 = -1;

    public static String getKeyName(int key) {
        if (key >= MOUSE) {
            return "M" + (key - MOUSE);
        }
        String keyName = GLFW.glfwGetKeyName(key, GLFW.glfwGetKeyScancode(key));
        if (keyName == null) {
            keyName = KEY_MAP.get(key);
        }
        return keyName;
    }

    @Init(level = 999999999)
    public void handle() {
        keyBinds0 = new ArrayList<>();
        keyBinds1 = new ArrayList<>();
        keyBinds2 = new ArrayList<>();
        active = new CopyOnWriteArrayList<>();
        for (Object o : configManager.getConfigsByClass(KeyBind.class)) {

            KeyBind keyBind = (KeyBind) o;
            switch (keyBind.getKeys().size()) {
                case 0: {
                    break;
                }
                case 1: {
                    keyBinds0.add(keyBind);
                    break;
                }
                case 2: {
                    keyBinds1.add(keyBind);
                    break;
                }
                case 3: {
                    keyBinds2.add(keyBind);
                    break;
                }
                default:
                    throw new RuntimeException("NMSL");
            }

        }
    }

    @EventTarget
    public void keyboard(KeyboardEvent e) {

        if (e.getAction() == 2) return;
        appendKeyboard(e.getKeyCode(), e.getAction());
        printKeyNames();
        matchKey();
    }

    @EventTarget
    public void mouse(MouseEvent e) {

        appendMouse(e.getButton(), e.getAction());
        printKeyNames();
        matchKey();
    }

    private void appendMouse(int value, int action) {
        value = value + MOUSE;
        if (action == 1) {
            if (key0 == -1) {
                key0 = value;
                return;
            } else if (key0 == value) {
                return;
            }
            if (key1 == -1) {
                key1 = value;
                return;
            }
            if (key1 == value) {
                return;
            }
            if (key2 == -1) {
                key2 = value;
                return;
            }
            if (key2 == value) {
                return;
            }
        }
        if (action == 0) {
            if (key0 == value) {
                key0 = -1;
                key1 = -1;
                key2 = -1;
            }
            if (key1 == value) {

                key1 = -1;
                key2 = -1;
            }
            if (key2 == value) {

                key2 = -1;
            }
        }

    }

    private void appendKeyboard(int value, int action) {
        if (action == 1) {

            if (key0 == -1) {
                key0 = value;
                return;
            } else if (key0 == value) {
                return;
            }
            if (key1 == -1) {
                key1 = value;
                return;
            }
            if (key1 == value) {
                return;
            }
            if (key2 == -1) {
                key2 = value;
                return;
            }
            if (key2 == value) {
                return;
            }
        }
        if (action == 0) {
            if (key0 == value) {
                key0 = -1;
                key1 = -1;
                key2 = -1;
            }
            if (key1 == value) {

                key1 = -1;
                key2 = -1;
            }
            if (key2 == value) {

                key2 = -1;
            }
        }
    }

    private void printKeyNames() {
        String key = "Keys" + "-> ";
        if (key0 != -1) {
            String nmsl = key0 >= MOUSE ? "MOUSE " + (key0 - MOUSE) : GLFW.glfwGetKeyName(key0, GLFW.glfwGetKeyScancode(key0));
            key += (nmsl == null ? KEY_MAP.get(key0) : nmsl);
        }
        if (key1 != -1) {
            String nmsl = key1 >= MOUSE ? "MOUSE " + (key1 - MOUSE) : GLFW.glfwGetKeyName(key1, GLFW.glfwGetKeyScancode(key1));
            key += "-> " + (nmsl == null ? KEY_MAP.get(key1) : nmsl);
        }
        if (key2 != -1) {
            String nmsl = key2 >= MOUSE ? "MOUSE " + (key2 - MOUSE) : GLFW.glfwGetKeyName(key2, GLFW.glfwGetKeyScancode(key2));
            key += "-> " + (nmsl == null ? KEY_MAP.get(key2) : nmsl);
        }
        if (key0 == -1 && key1 == -1 && key2 == -1) key = key + "clear!";
        logger.info(key.toUpperCase());
    }

    public void matchKey() {
        if (MinecraftClient.getInstance().currentScreen != null)
            return;
        if (key0 != -1) {
            for (KeyBind keyBind : keyBinds0) {
                matchKey1(keyBind);
            }
            if (key1 != -1) {
                for (KeyBind keyBind : keyBinds1) {
                    matchKey1(keyBind);
                }
                if (key2 != -1) {
                    for (KeyBind keyBind : keyBinds2) {
                        matchKey1(keyBind);
                    }
                }
            }
        }

    }

    private void matchKey1(KeyBind keyBind) {
        int keysize = keyBind.getKeys().size();
        if (keysize == 0) {
            return;
        }
        int flag = 0;
        if (keysize == 1 && key0 == keyBind.getKeys().get(0)) {
            flag++;
        }
        if (keysize == 2 && key1 == keyBind.getKeys().get(1)) {
            flag++;
        }
        if (keysize == 3 && key2 == keyBind.getKeys().get(2)) {
            flag++;
        }

        if (flag == keysize) {
//            logger.info("keybind: "+keyBind.getName()+" keys: "+getKeyName(key0)+" "+getKeyName(key1)+" "+getKeyName(key2));
            key0 = -1;
            key1 = -1;
            key2 = -1;

//            printKeyNames();
            keyBind.getRun().run();
        }
    }


}
