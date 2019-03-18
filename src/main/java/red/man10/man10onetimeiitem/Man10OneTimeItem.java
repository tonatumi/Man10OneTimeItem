package red.man10.man10onetimeiitem;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;


import java.util.HashMap;

public final class Man10OneTimeItem extends JavaPlugin {
    String prefix = "[§dM§f.§aO§f.§dT§f.§aI§r]";

    HashMap<String, BoxInfo> boxdata = new HashMap<String, BoxInfo>();

    //helpの表示
    public void showHelp(Player p) {
        p.sendMessage("§e-------§r" + prefix + "§e-------§r");
        p.sendMessage("§6§l/moti create [BOX名]§r:[BOX名]の箱を作成");
        p.sendMessage("§6§l/moti additem [BOX名]§r:[BOX名]に右手に持っているアイテムを追加");
        p.sendMessage("§6§l/moti removeitem [BOX名] [番号]§r:[BOX名]から[list番号のアイテム]を削除");
        p.sendMessage("§6§l/moti delete [BOX名]§r:[BOX名]を削除");
        p.sendMessage("§6§l/moti list§r:箱のリストを表示");
        p.sendMessage("§6§l/moti itemlist [BOX名]§r:[BOX名]のアイテムリストを表示");
        p.sendMessage("§6§l/moti get [BOX名]§r:[BOX名]を取得");
    }

    //ボックスリストの表示
    public void boxList(Player p) {
        p.sendMessage("-------ボックスリスト-------");
        boxdata.forEach((key, value) -> p.sendMessage(key));
    }

    //ボックス名が存在しない
    public void notExistBox(Player p){
        p.sendMessage(prefix + "§cそのボックス名は存在しません！");
    }

    //ボックス名を要求
    public void demandBoxname(Player p) {
        p.sendMessage(prefix + "§cボックス名を入力してください。");
    }

    //既にそのボックス名が存在している
    public void alreadyBeBoxname(Player p) {
        p.sendMessage(prefix + "§cそのボックス名は既に存在しています！");
    }

    //ボックス作成
    public void createBox(Player p, String args) {
        String boxname = args;
        ItemStack box = p.getInventory().getItemInMainHand();
        BoxInfo boxinfo = new BoxInfo();
        boxinfo.boxItem = box;
        boxdata.put(boxname, boxinfo);
        p.sendMessage(prefix + "§aボックス" + boxname + "§aを作成しました。");
    }

    //ボックスにアイテムを追加
    public void additemToBox(Player p,String args) {
        BoxInfo boxinfo = boxdata.get(args);
        boxinfo.contentsItems.add(p.getInventory().getItemInMainHand());
        boxdata.put(args, boxinfo);
        p.sendMessage(prefix + "§aアイテムを追加しました！");
    }

    //ボックスを削除
    public void deleteBox(Player p, String args) {
        boxdata.remove(args);
        p.sendMessage(prefix + "§aボックス" + "§f" + args + "§aを削除しました!");
    }

    //アイテムリストを表示
    public void showItemList(Player p, String args) {
        p.sendMessage(prefix + args + "の中身");
        int i = 0;
        for (ItemStack itemlist : boxdata.get(args).contentsItems) {
            if (itemlist.hasItemMeta()) {
                p.sendMessage(i + ":" + itemlist.getItemMeta().getDisplayName());
            } else {
                p.sendMessage(i + ":" + itemlist.getType().toString());
            }
            i++;
        }
    }

    //ボックスを与える
    public void giveBox(Player p,String args) {
        p.getInventory().addItem(boxdata.get(args).boxItem);
    }

    //そのアイテム番号は存在しない
    public void notExistItemNum(Player p) {
        p.sendMessage(prefix + "§cそのアイテム番号は存在しません！/moti itemlist [Box名]");
    }

    //boxからアイテムを削除
    public void removeItem(Player p, String[] args) {
        BoxInfo boxinfo = boxdata.get(args[1]);
        int removeNum;
        try {
            removeNum = Integer.parseInt(args[2]);
        } catch (NumberFormatException n) {
            p.sendMessage(prefix + "§c消したいアイテムをリスト番号の数字で指定してください！");
            return;
        }
        boxdata.get(args[1]).contentsItems.remove(removeNum);
        boxdata.put(args[1], boxinfo);
        p.sendMessage(prefix + "§aボックス名§6" + args[1] + "§aからアイテム番号" + "§6" + args[2] + "§aを削除しました！");
    }

    //引数が足りないかコマンドがおかしい時
    public void wrongCommand(Player p) {
        p.sendMessage(prefix + "§c引数が足りないかコマンドが不正です！/moti");
    }

    //引数が長すぎるとき
    public void tooLongCommand(Player p) {
        p.sendMessage(prefix + "§c引数が長すぎます！/moti");
    }


    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("start:Man10OneTimeItem");
        getCommand("moti").setExecutor(new MotiCommand(this));
        getServer().getPluginManager().registerEvents(new Events(this), this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.saveConfig();
        getLogger().info("stop:Man10OneTimeItem");
    }


}