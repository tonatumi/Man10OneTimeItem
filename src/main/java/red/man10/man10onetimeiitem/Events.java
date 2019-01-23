package red.man10.man10onetimeiitem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class Events implements Listener {
    Man10OneTimeItem moti;
    public Events(Man10OneTimeItem moti){
        this.moti = moti;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        BoxInfo boxinfo = new BoxInfo();
        Player p = e.getPlayer();
        boolean flag = false;
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK){
            ItemStack items = p.getInventory().getItemInMainHand() ;
            for(Map.Entry<String, BoxInfo> entry : moti.boxdata.entrySet()){
                ItemStack a = entry.getValue().boxItem;
                if(items.getType() == a.getType()) {
                    if (items.getItemMeta() == null || items.getItemMeta().toString().equalsIgnoreCase(a.getItemMeta().toString())) {
                        flag = true;
                        e.setCancelled(true);
                        boxinfo = entry.getValue();
                        break;
                    }
                }
            }

            if(flag){
                if(countEmpty(p.getInventory()) < boxinfo.contentsItems.size()){
                    p.sendMessage(moti.prefix+"§cインベントリーを空けてください！");
                    return;
                }
                if(p.getInventory().getItemInMainHand().getAmount()!=1){
                    p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
                }
                else {
                        p.getInventory().setItemInMainHand(null);
                }
                for(ItemStack giveitem:boxinfo.contentsItems){
                    p.getInventory().addItem(giveitem);
                }
                return;
            }
            else{
                return;
            }
        }
    }

    public int countEmpty (Inventory inv){
        int count =0;
        for(int i=0; i<=35; i++){
            if(inv.getItem(i) == null){
                count++;
            }
        }
        return count;
    }
}