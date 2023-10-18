package WizardTD;

import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class trywave {
    public int duration;
    public float pre_wave_pause;
    public List<MonsterInfo> monsters;

    public trywave(JSONObject waveData) {
        this.duration = waveData.getInt("duration");
        this.pre_wave_pause = waveData.getFloat("pre_wave_pause");

        monsters = new ArrayList<>();

        JSONArray monstersArray = waveData.getJSONArray("monsters");
        for (int i = 0; i < monstersArray.size(); i++) {
            JSONObject monsterData = monstersArray.getJSONObject(i);
            String type = monsterData.getString("type");
            int hp = monsterData.getInt("hp");
            float speed = monsterData.getFloat("speed");
            float armour = monsterData.getFloat("armour");
            int manaGainedOnKill = monsterData.getInt("mana_gained_on_kill");
            int quantity = monsterData.getInt("quantity");


            MonsterInfo monsterInfo = new MonsterInfo(type, hp, speed, armour, manaGainedOnKill, quantity);
            monsters.add(monsterInfo);
        }
        //System.out.println("Number of monsters in this wave: " + monsters.size());
    }

}
