package transfarmer.soulboundarmory.item;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import transfarmer.soulboundarmory.capability.weapon.SoulWeaponProvider;

import static transfarmer.soulboundarmory.statistics.weapon.SoulWeaponDatum.WEAPON_DATA;
import static transfarmer.soulboundarmory.statistics.weapon.SoulWeaponType.GREATSWORD;

public class ItemSoulGreatsword extends ItemSoulWeapon {
    public ItemSoulGreatsword() {
        super(3, -2.8F, 3);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 200;
    }

    @Override
    public EnumAction getItemUseAction(final ItemStack stack) {
        return EnumAction.BOW;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(final World world, final EntityPlayer player, final EnumHand hand) {
        if (!world.isRemote && player != null && SoulWeaponProvider.get(player).getDatum(WEAPON_DATA.skills, GREATSWORD) >= 1) {
            player.setActiveHand(hand);
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
        }

        return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(hand));
    }

    @Override
    public void onPlayerStoppedUsing(final ItemStack itemStack, final World world, final EntityLivingBase entity, final int timeLeft) {
        final int timeTaken = 200 - timeLeft;

        if (timeTaken > 5) {
            final Vec3d look = entity.getLookVec();
            final double strength = Math.min(1.25, timeTaken / 15F * 1.25);

            entity.addVelocity(look.x * strength, look.y * strength / 4 + 0.2, look.z * strength);
        }
    }


    @Override
    public void onUpdate(final ItemStack itemStack, final World world, final Entity entity, final int itemSlot, final boolean isSelected) {
        if (world.isRemote && isSelected && entity instanceof EntityPlayerSP) {
            final EntityPlayerSP player = (EntityPlayerSP) entity;
            final ItemStack activeStack = player.getActiveItemStack();

            if (!activeStack.isEmpty() && activeStack.getItem() == this) {
                player.movementInput.moveForward *= 4.5;
                player.movementInput.moveStrafe *= 4.5;
            }
        }
    }
}
