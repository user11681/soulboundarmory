package user11681.soulboundarmory.capability.soulbound.player;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import user11681.soulboundarmory.capability.soulbound.item.ItemStorage;
import user11681.soulboundarmory.item.SoulboundWeaponItem;

public class SoulboundItemUtil {
    public static ItemStorage<?> getFirstStorage(Entity entity) {
        if (entity == null) {
            return null;
        }

        for (ItemStack itemStack : entity.getHandSlots()) {
            ItemStorage<?> component = ItemStorage.get(entity, itemStack.getItem());

            if (component != null) {
                return component;
            }
        }

        return null;
    }

    public static boolean isSoulWeaponEquipped(PlayerEntity player) {
        return player.getMainHandItem().getItem() instanceof SoulboundWeaponItem
            || player.getOffhandItem().getItem() instanceof SoulboundWeaponItem;
    }

    public static boolean addItemStack(ItemStack itemStack, PlayerEntity player) {
        return addItemStack(itemStack, player, true);
    }

    public static boolean addItemStack(ItemStack itemStack, PlayerEntity player, boolean hasReservedSlot) {
        return true;
/*
         PlayerInventory inventory = player.inventory;

        if (!(itemStack.getItem() instanceof SoulboundItem)) {
            hasReservedSlot = false;
        }

        if (!hasReservedSlot) {
            int slot = inventory.getOccupiedSlotWithRoomForStack(itemStack);

            if (slot == -1) {
                slot = inventory.getEmptySlot();
            }

             int size = inventory.main.size();
             List<ItemStack> mergedInventory = CollectionUtil.merge(NonNullList.of(), inventory.main, inventory.offHand);

            if (slot != -1) {
                 ItemStack slotStack = slot != 40 ? mergedInventory.get(slot) : mergedInventory.get(size);
                 int transferred = Math.min(slotStack.getMaxCount() - slotStack.getCount(), itemStack.getCount());

                itemStack.setCount(itemStack.getCount() - transferred);
                slotStack.setCount(slotStack.getCount() + transferred);

                if (!itemStack.isEmpty()) {
                    return addItemStack(itemStack, player, false);
                }

                return true;
            }

            for (ComponentType<? extends SoulboundComponent> type : Components.SOULBOUND_COMPONENTS) {
                 SoulboundComponent component = type.get(player);

                for (ItemStorage<?> storage : component.getStorages().values()) {
                    for (int index = 0; index < mergedInventory.size(); index++) {
                        if (storage.getBoundSlot() != index && mergedInventory.get(index).isEmpty()) {
                            if (index != size) {
                                return inventory.insertStack(index, itemStack);
                            }

                            if (player.getOffHandStack().isEmpty() && Components.CONFIG_COMPONENT.get(player).getAddToOffhand()) {
                                inventory.setInvStack(40, itemStack);
                                itemStack.setCount(0);
                            }
                        }
                    }
                }
            }

            return false;
        }

         int boundSlot = ItemStorage.get(player, itemStack.getItem()).getBoundSlot();

        if (boundSlot >= 0) {
            if (inventory.getInvStack(boundSlot).isEmpty()) {
                return inventory.insertStack(boundSlot, itemStack);
            }
        } else {
             ItemStack mainhandStack = player.getMainHandStack();

            if (mainhandStack.isEmpty()) {
                return inventory.insertStack(ItemUtil.getSlotFor(inventory, mainhandStack), itemStack);
            }
        }

        return addItemStack(itemStack, player, false);
*/
    }

    public static boolean hasSoulWeapon(PlayerEntity player) {
        int size = player.inventory.getContainerSize();
        ItemStack[] inventory = player.inventory.items.toArray(new ItemStack[size + 1]);
        inventory[size] = player.getOffhandItem();

        for (ItemStack itemStack : inventory) {
            if (itemStack != null && itemStack.getItem() instanceof SoulboundWeaponItem) {
                return true;
            }
        }

        return false;
    }
}
