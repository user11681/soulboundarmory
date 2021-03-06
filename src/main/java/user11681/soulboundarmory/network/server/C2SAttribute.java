package user11681.soulboundarmory.network.server;

import net.minecraftforge.fml.network.NetworkEvent;
import user11681.soulboundarmory.capability.soulbound.item.ItemStorage;
import user11681.soulboundarmory.capability.statistics.StatisticType;
import user11681.soulboundarmory.network.ExtendedPacketBuffer;
import user11681.soulboundarmory.network.ItemComponentPacket;

public class C2SAttribute implements ItemComponentPacket {
    @Override
    public void execute(ExtendedPacketBuffer buffer, NetworkEvent.Context context, ItemStorage<?> storage) {
        storage.incrementPoints(StatisticType.registry.getValue(buffer.readResourceLocation()), buffer.readInt());
        storage.refresh();
    }
}
