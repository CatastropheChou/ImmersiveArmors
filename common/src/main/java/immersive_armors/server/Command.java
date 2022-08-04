package immersive_armors.server;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.util.Formatting.*;

public class Command {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("immersive-armors")
                .then(register("help", Command::displayHelp))
                .requires((serverCommandSource) -> serverCommandSource.hasPermissionLevel(2))
        );
    }

    private static int displayHelp(CommandContext<ServerCommandSource> ctx) {
        Entity player = ctx.getSource().getEntity();
        if (player == null) {
            return 0;
        }

        sendMessage(player, DARK_RED + "--- " + GOLD + "COMMANDS" + DARK_RED + " ---");
        sendMessage(player, WHITE + " /immersive-armors help " + GOLD + " - Displays this help.");

        return 0;
    }

    private static ArgumentBuilder<ServerCommandSource, ?> register(String name, com.mojang.brigadier.Command<ServerCommandSource> cmd) {
        return CommandManager.literal(name).requires(cs -> cs.hasPermissionLevel(0)).executes(cmd);
    }

    private static ArgumentBuilder<ServerCommandSource, ?> register(String name) {
        return CommandManager.literal(name).requires(cs -> cs.hasPermissionLevel(0));
    }

    private static void success(String message, CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendFeedback(Text.literal(message).formatted(GREEN), true);
    }

    private static void fail(String message, CommandContext<ServerCommandSource> ctx) {
        ctx.getSource().sendError(Text.literal(message).formatted(RED));
    }


    private static void sendMessage(Entity commandSender, String message) {
        commandSender.sendMessage(Text.literal(message));
    }
}
