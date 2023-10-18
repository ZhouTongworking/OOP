package WizardTD;

import processing.data.JSONArray;
import processing.data.JSONObject;

import java.io.File;
import java.util.ArrayList;

import static processing.core.PApplet.loadJSONObject;

public class Readconf {
    String layout;
    int duration;
    float pre_wave_pause;
    String type;
    int hp;
    float speed;
    double armour;
    int mana_gained_on_kill;
    int quantity;
    int initial_tower_range;
    double initial_tower_firing_speed;
    int initial_tower_damage;
    int initial_mana;
    int initial_mana_cap;
    int initial_mana_gained_per_second;
    int tower_cost;
    int mana_pool_spell_initial_cost;
    int mana_pool_spell_cost_increase_per_use;
    double mana_pool_spell_cap_multiplier;
    double mana_pool_spell_mana_gained_multiplier;


    public Readconf(){
        JSONObject configData = loadJSONObject(new File("config.json"));
        layout = configData.getString("layout");
        initial_tower_range = configData.getInt("initial_tower_range");
        initial_tower_firing_speed = configData.getDouble("initial_tower_firing_speed");
        initial_tower_damage = configData.getInt("initial_tower_damage");
        initial_mana = configData.getInt("initial_mana");
        initial_mana_cap = configData.getInt("initial_mana_cap");
        initial_mana_gained_per_second = configData.getInt("initial_mana_gained_per_second");
        tower_cost = configData.getInt("tower_cost");
        mana_pool_spell_initial_cost = configData.getInt("mana_pool_spell_initial_cost");
        mana_pool_spell_cost_increase_per_use = configData.getInt("mana_pool_spell_cost_increase_per_use");
        mana_pool_spell_cap_multiplier = configData.getDouble("mana_pool_spell_cap_multiplier");
        mana_pool_spell_mana_gained_multiplier = configData.getDouble("mana_pool_spell_mana_gained_multiplier");


        JSONArray waves = configData.getJSONArray("waves");
        for (int i = 0; i < waves.size(); i++) {
            JSONObject wave = waves.getJSONObject(i);
            duration = wave.getInt("duration");
            pre_wave_pause = wave.getFloat("pre_wave_pause");
            JSONArray monsters = wave.getJSONArray("monsters");
            for (int j = 0; j < monsters.size(); j++) {
                JSONObject monster = monsters.getJSONObject(j);
                type = monster.getString("type");
                hp = monster.getInt("hp");
                speed = monster.getInt("speed");
                armour = monster.getDouble("armour");
                mana_gained_on_kill = monster.getInt("mana_gained_on_kill");
                quantity = monster.getInt("quantity");
            }
        }

    }
}
