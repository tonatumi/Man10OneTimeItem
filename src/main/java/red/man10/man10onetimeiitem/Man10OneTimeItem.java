package red.man10.man10onetimeiitem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class Man10OneTimeItem extends JavaPlugin {

    String prefix = "[§dM§f.§aO§f.§dT§f.§aI§r]";
    HashMap<String, BoxInfo> boxdata = new HashMap<String,BoxInfo>();

    @Override
    public boolean onCommand (CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }
        Player p = (Player)sender;
        if(args.length == 0){
            p.sendMessage("§e-------§r"+prefix+"§e-------§r");
            p.sendMessage("§6§l/moti create [BOX名]§r:[BOX名]の箱を作成");
            p.sendMessage("§6§l/moti additem [BOX名]§r:[BOX名]に右手に持っているアイテムを追加");
            p.sendMessage("§6§l/moti removeitem [BOX名] [番号]§r:[BOX名]から[list番号のアイテム]を削除");
            p.sendMessage("§6§l/moti delete [BOX名]§r:[BOX名]を削除");
            p.sendMessage("§6§l/moti list§r:箱のリストを表示");
            p.sendMessage("§6§l/moti itemlist [BOX名]§r:[BOX名]のアイテムリストを表示");
            p.sendMessage("§6§l/moti get [BOX名]§r:[BOX名]を取得");

        }else {
            switch (args[0]) {
                case "create":
                    if(args.length == 1) {
                        p.sendMessage(prefix + "§cボックス名を入力してください。");
                        return true;
                    }else if(args.length >3){
                        p.sendMessage(prefix+"§c引数が長すぎます！");
                        return true;
                    }else if(boxdata.containsKey(args[1])) {
                        p.sendMessage(prefix+"§cそのボックス名は既に存在しています！");
                        return true;
                    }else {
                        String boxname = args[1];
                        ItemStack box = p.getInventory().getItemInMainHand();
                        BoxInfo boxinfo = new BoxInfo();
                        boxinfo.boxItem = box;
                        boxdata.put(boxname, boxinfo);
                        p.sendMessage(prefix + "§aボックス" + boxname + "§aを作成しました。");
                        return true;
                    }
                case "additem":
                    if (args.length == 1) {
                        p.sendMessage(prefix + "§cボックス名を入力してください");
                        return true;
                    }else if(args.length >3){
                        p.sendMessage(prefix+"§c引数が長すぎます！");
                        return true;
                    }else if(boxdata.containsKey(args[1])){
                        BoxInfo boxinfo = boxdata.get(args[1]);
                        boxinfo.contentsItems.add(p.getInventory().getItemInMainHand());
                        boxdata.put(args[1], boxinfo);
                        p.sendMessage(prefix+"§aアイテムを追加しました！");
                        return true;
                    } else {
                        p.sendMessage(prefix+"§cそのボックス名は存在しません！");
                        return true;
                    }
                case "removeitem":
                    if (args.length == 1) {
                        p.sendMessage(prefix + "§cボックス名を入力してください");
                        return true;
                    }else if (args.length >3) {
                        p.sendMessage(prefix+"§c引数が長すぎます！");
                        return true;
                    }else if (boxdata.containsKey(args[1])){
                        BoxInfo boxinfo = boxdata.get(args[1]);
                        try {
                            boxinfo.contentsItems.remove(args[2]);
                        }catch (IllegalArgumentException a){
                            p.sendMessage(prefix+"§c消したいアイテムをリストの順番の数字で指定してください！");
                            p.sendMessage("§cエラー内容："+a);
                            return true;
                        }
                        boxdata.put(args[1], boxinfo);
                        p.sendMessage(prefix+"§aボックス名§6"+args[1]+"§aからアイテム番号"+"§6"+args[2]+"§aを削除しました！");
                        return true;
                    }else {
                        p.sendMessage(prefix+"§cそのボックス名は存在しません！");
                        return true;
                    }
                case "delete":
                    if (args.length == 1) {
                        p.sendMessage(prefix + "§cボックス名を入力してください");
                        return true;
                    }else if(args.length >3){
                        p.sendMessage(prefix+"§c引数が長すぎます！");
                        return true;
                    }else {
                        boxdata.remove(args[1]);
                        return true;
                    }
                case "list":
                    if(args.length >2){
                        p.sendMessage(prefix+"§c引数が長すぎます！");
                        return true;
                    }else {
                        p.sendMessage("-------ボックスリスト-------");
                        boxdata.forEach((key, value) -> p.sendMessage(key));
                        return true;
                    }
                case "itemlist":
                    if (args.length == 1) {
                        p.sendMessage(prefix + "§cボックス名を入力してください");
                        return true;
                    }else if (args.length >3){
                        p.sendMessage(prefix+"§c引数が長すぎます！");
                    }else{
                        p.sendMessage(prefix+args[1]+"の中身");
                        int i =0;
                        for(ItemStack itemlist:boxdata.get(args[1]).contentsItems) {
                            if (itemlist.hasItemMeta()) {
                                p.sendMessage(i + ":" + itemlist.getItemMeta().getDisplayName());
                            } else {
                                p.sendMessage(i + ":" + itemlist.getType().toString());
                            }
                            i++;
                        }
                        return true;
                    }
                case "get":
                    if(args.length == 1) {
                        p.sendMessage(prefix + "§cボックス名を入力してください");
                        return true;
                    }else if (args.length >3){
                        p.sendMessage(prefix+"§c引数が長すぎます！");
                        return true;
                    }else {
                        p.getInventory().addItem(boxdata.get(args[1]).boxItem);
                    return true;
                    }
                default:


            }
        }
        return true;
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("start:Man10OneTimeItem");
        getServer().getPluginManager().registerEvents(new Events(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("stop:Man10OneTimeItem");
    }
}
