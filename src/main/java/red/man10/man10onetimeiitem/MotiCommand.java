package red.man10.man10onetimeiitem;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MotiCommand implements CommandExecutor {

    private final Man10OneTimeItem plugin;

    public MotiCommand(Man10OneTimeItem plugin){
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){

        //コマンドの送り主が実際のプレイヤーではないときの処理
        if (!(sender instanceof Player)) {
            return false;
        }

        Player p = (Player) sender;

        //コマンドがmotiのみのとき
        if(args.length == 0){
            plugin.showHelp(p);
            return true;
        }

        //引数が1つの時
        if(args.length == 1 ){
            //listのとき
            if(args[0].equalsIgnoreCase("list")){
                plugin.boxList(p);
                return true;
            }
            //引数が足りないかコマンドが不正の場合
            plugin.wrongCommand(p);
            return true;
        }

        //引数が2の時
        if(args.length ==2){
            //createの時
            if(args[0].equalsIgnoreCase("create")){
                //既にそのボックス名があるとき
                if(plugin.boxdata.containsKey(args[1])){
                    plugin.alreadyBeBoxname(p);
                    return true;
                }
                plugin.createBox(p, args[1]);
                return true;
            }

            //additemの時
            if(args[0].equalsIgnoreCase("additem")){
                if(plugin.boxdata.containsKey(args[1])){
                    plugin.additemToBox(p, args[1]);
                    return true;
                }
                //ボックス名が存在しないとき
                plugin.notExistBox(p);
                return true;
            }

            //deleteのとき
            if(args[0].equalsIgnoreCase("delete")) {
                if (plugin.boxdata.containsKey(args[1])) {
                    plugin.deleteBox(p, args[1]);
                    return true;
                }
                //ボックス名が存在しないとき
                plugin.notExistBox(p);
                return true;
            }

            //itemlistの時
            if(args[0].equalsIgnoreCase("itemlist")){
                if(plugin.boxdata.containsKey(args[1])){
                    plugin.showItemList(p,args[1]);
                    return true;
                }
                //ボックス名が存在しないとき
                plugin.notExistBox(p);
                return true;
            }
            //getの時
            if(args[0].equalsIgnoreCase("get")){
                if(plugin.boxdata.containsKey(args[1])){
                    plugin.giveBox(p,args[1]);
                    return true;
                }
                //ボックス名が存在しないとき
                plugin.notExistBox(p);
                return true;
            }
            plugin.wrongCommand(p);
            return true;
        }

        if(args.length ==3){

            //removeitemの時
            if(args[0].equalsIgnoreCase("removeitem")){
                if(plugin.boxdata.containsKey(args[1])){
                    if(Integer.parseInt(args[2]) <= plugin.boxdata.get(args[1]).contentsItems.size()){
                        plugin.removeItem(p,args);
                        return true;
                    }
                    //アイテム番号が存在しないとき
                    plugin.notExistItemNum(p);
                    return true;
                }
                //ボックス名が存在しないとき
                plugin.notExistBox(p);
                return true;
            }
            plugin.wrongCommand(p);
            return true;
        }

        //引数が4以上のとき
        plugin.tooLongCommand(p);
        return true;
    }
}
