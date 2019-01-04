package red.man10.man10onetimeiitem;

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
    Man10OneTimeItem moti = new Man10OneTimeItem();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e){
        BoxInfo boxinfo = new BoxInfo();
        Player p = e.getPlayer();
        p.sendMessage("aaaaa");
        Boolean flag = false;
        if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction()==Action.RIGHT_CLICK_BLOCK){
            for(Map.Entry<String, BoxInfo> entry : moti.boxdata.entrySet()){
                if(p.getInventory().getItemInMainHand() == entry.getValue().boxItem){
                    flag = true;
                    e.setCancelled(true);
                    boxinfo = entry.getValue();
                    break;
                }
            }
            if(flag){
                if(countEmpty(p.getInventory()) < boxinfo.contentsItems.size()){
                    p.sendMessage(moti.prefix+"§cインベントリーを空けてください！");
                    return;
                }
                p.getInventory().getItemInMainHand().setAmount(p.getInventory().getItemInMainHand().getAmount()-1);
                for(ItemStack giveitem:boxinfo.contentsItems){
                    p.getInventory().addItem(giveitem);
                }
                return;
            }else{
                return;
            }
        }
    }

    public int countEmpty (Inventory inv){
        int count =0;
        for(ItemStack invitem:inv){
            if(invitem.getType() == Material.AIR){
                count++;
            }
        }
        return count;
    }
}