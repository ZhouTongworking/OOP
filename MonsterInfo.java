package WizardTD;

public class MonsterInfo {
    private String type;
    private int hp;
    private float speed;
    private float armour;
    private int mana_gained_on_kill;
    private int quantity;

    public MonsterInfo(String type, int hp, float speed, float armour, int mana_gained_on_kill, int quantity) {
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.armour = armour;
        this.mana_gained_on_kill = mana_gained_on_kill;
        this.quantity = quantity;
    }

    public String get_type() {
        return type;
    }

    public int get_hp() {
        return hp;
    }

    public float get_speed() {
        return speed;
    }

    public float get_armour() {
        return armour;
    }

    public int get_mana_gained_on_kill() {
        return mana_gained_on_kill;
    }

    public int get_quantity() {
        return quantity;
    }

}
