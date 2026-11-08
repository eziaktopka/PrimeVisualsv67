package primevisuals;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;

public class PrimeVisualsClient implements ClientModInitializer {
    public static KeyBinding OPEN;

    @Override
    public void onInitializeClient() {
        OPEN = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.primevisuals.open", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, "category.primevisuals"));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (OPEN.wasPressed()) {
                if (client.currentScreen == null) {
                    client.setScreen(new TexScreen());
                }
            }
            Fx.tick(client);
        });
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (world.isClient()) {
                Fx.onAttack(player, entity);
            }
            return ActionResult.PASS;
        });
    }
}
