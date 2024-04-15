package velodicord.events;

import com.velocitypowered.api.event.PostOrder;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.connection.DisconnectEvent;
import net.dv8tion.jda.api.EmbedBuilder;
import velodicord.Config;
import velodicord.VOICEVOX;
import velodicord.discordbot;

import java.awt.*;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.NamedTextColor.AQUA;
import static net.kyori.adventure.text.format.NamedTextColor.YELLOW;


import static velodicord.Velodicord.velodicord;

public class Disconnect {
    @Subscribe(order = PostOrder.FIRST)
    public void onDisconnect(DisconnectEvent event) {
        String player = event.getPlayer().getUsername();
        velodicord.getProxy().sendMessage(text()
                .append(text("["+player+"]", AQUA))
                .append(text("が退出しました", YELLOW))
        );
        discordbot.LogChannel.sendMessageEmbeds(new EmbedBuilder()
                .setTitle("退出しました")
                .setColor(Color.blue)
                .setAuthor(player, null, "https://mc-heads.net/avatar/"+player+".png")
                .build()).queue();
        String message = player+"がマイクラサーバーから退出しました";
        for (String word : Config.dic.keySet()) {
            message = message.replace(word, Config.dic.get(word));
        }
        discordbot.sendvoicemessage(message, Config.minespeaker.getOrDefault(event.getPlayer().getUniqueId().toString(), Integer.valueOf(Config.config.get("DefaultSpeakerID"))));
    }
}
