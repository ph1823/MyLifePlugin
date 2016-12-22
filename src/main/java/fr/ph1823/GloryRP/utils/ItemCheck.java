package fr.ph1823.GloryRP.utils;

import fr.ph1823.GloryRP.GloryRP1;

import java.util.ArrayList;

/**
 * Created by ph1823 - Pierre-Henr on 17/08/2016.
 */
public class ItemCheck {

    public static GloryRP1 plugin;
    private int number;
    private int Id;
    private byte Data;
    private String Reason;
    private String World;
    private String wWorld;
    private int check;

    public ItemCheck(GloryRP1 instance) {
        plugin = instance;
    }

    public ItemCheck(ArrayList<String> ls, int id, byte data, String world) {
        for (int x = 0; x < ls.size(); ++x) {
            String string = (String) ls.get(x);
            String[] seperated = string.split(":");
            this.Id = Integer.parseInt(seperated[0]);
            this.Data = Byte.parseByte(seperated[1]);
            this.check = 1;
            if (seperated.length == 3) {
                if (this.Id == id) {
                    if (this.Data == data) {
                        this.number = 1;
                        this.Reason = seperated[2].toString();
                        this.wWorld = "All";
                        break;
                    }

                    if (this.Data == -1) {
                        this.number = 1;
                        this.Reason = seperated[2].toString();
                        this.wWorld = "All";
                        break;
                    }

                    this.number = 0;
                } else {
                    this.number = 0;
                }
            } else if (seperated.length == 4) {
                this.World = seperated[2].toString().toLowerCase();
                if (!this.World.equalsIgnoreCase(world) && !this.World.equalsIgnoreCase("global")) {
                    this.check = 0;
                } else {
                    this.check = 1;
                }

                if (this.Id == id) {
                    if (this.Data == data) {
                        this.number = 1;
                        this.Reason = seperated[3].toString();
                        this.wWorld = this.World;
                        break;
                    }

                    if (this.Data == -1) {
                        this.number = 1;
                        this.Reason = seperated[3].toString();
                        this.wWorld = this.World;
                        break;
                    }

                    this.number = 0;
                } else {
                    this.number = 0;
                }
            }
        }

    }

    public int getId() {
        return this.Id;
    }

    public int getData() {
        return this.Data;
    }

    public String getReason() {
        return this.Reason;
    }

    public int getnumber() {
        return this.number;
    }

    public String getWorld() {
        return this.World;
    }

    public String getwWorld() {
        return this.wWorld;
    }

    public int worldcheck() {
        return this.check;
    }
}
