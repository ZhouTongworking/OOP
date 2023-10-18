package WizardTD;

import processing.core.PApplet;
import processing.data.JSONArray;
import processing.data.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Wave {
    PApplet pApplet;
    public int duration;
    public float pre_wave_pause;
    private int currentWaveIndex;
    private int generatedMonsterCount;
    private int frameCount;
    public int frame;
    private List<Monster> monsters;
    private List<WaveInfo> wavesInfo;
    private List<MonsterInfo> monstersInfo;
    public Wave(JSONObject waveInfo) {
        //this.pApplet = pApplet;
        this.duration =  waveInfo.getInt("duration");
        this.pre_wave_pause = waveInfo.getFloat("pre_wave_pause");
        this.monstersInfo = parseMonsterData(waveInfo.getJSONArray("monsters"));
        currentWaveIndex = 0;
        generatedMonsterCount = 0;
        frameCount = 0;
        this.wavesInfo = new ArrayList<>();
    }
//    private List<WaveInfo> parseWaveInfo(JSONArray wavesArray) {
//        System.out.println("Parsing waves data...");
//        System.out.println("JSON Array: " + wavesArray);
//        List<WaveInfo> waveInfoList = new ArrayList<>();
//        for (int i = 0; i < wavesArray.size(); i++) {
//            JSONObject waveData = wavesArray.getJSONObject(i);
//            int duration = waveData.getInt("duration");
//            float preWavePause = waveData.getFloat("pre_wave_pause");
//            List<MonsterInfo> monstersInfo = parseMonsterData(waveData.getJSONArray("monsters"));
//
//            WaveInfo waveInfo = new WaveInfo(duration, preWavePause, monstersInfo);
//            waveInfoList.add(waveInfo);
//        }
//        return waveInfoList;
//    }


    private List<MonsterInfo> parseMonsterData(JSONArray monstersArray) {
        List<MonsterInfo> monsterList = new ArrayList<>();
        for (int j = 0; j < monstersArray.size(); j++) {
            JSONObject monsterData = monstersArray.getJSONObject(j);
            String type = monsterData.getString("type");
            int hp = monsterData.getInt("hp");
            float speed = monsterData.getFloat("speed");
            Float armour = monsterData.getFloat("armour");
            int manaGainedOnKill = monsterData.getInt("mana_gained_on_kill");
            int quantity = monsterData.getInt("quantity");

            MonsterInfo monsterInfo = new MonsterInfo(type, hp, speed, armour, manaGainedOnKill, quantity);
            monsterList.add(monsterInfo);
        }
        return monsterList;
    }
    public static List<Wave> parseWaves(JSONArray wavesArray) {
        List<Wave> waves = new ArrayList<>();
        for (int i = 0; i < wavesArray.size(); i++) {
            JSONObject waveData = wavesArray.getJSONObject(i);
            Wave wave = new Wave(waveData);
            waves.add(wave);
        }
        return waves;
    }
    public void update() {
        if(wavesInfo == null){
            System.out.println("null");
        }

        if (currentWaveIndex < wavesInfo.size()) {
            frameCount++;

            //  next frame
            float framesPerSecond = pApplet.frameRate;
            float monsterSpawnInterval = wavesInfo.get(currentWaveIndex).getDuration() / (float) monstersInfo.size();
            int framesForNextMonster = (int) (monsterSpawnInterval * framesPerSecond);

            //  monster to next frame
            if (frameCount >= framesForNextMonster && generatedMonsterCount < monsters.size()) {
                Monster monster = monsters.get(generatedMonsterCount);
                monster.move();
                monster.draw(pApplet);
                generatedMonsterCount++;
                frameCount = 0; // resize

                //
                if (generatedMonsterCount >= monsters.size()) {
                    currentWaveIndex++;
                    generatedMonsterCount = 0; //
                }
            }
        }
    }


    public int getDuration() {
        return duration;
    }

    public float getPreWavePause() {
        return pre_wave_pause;
    }

    public List<MonsterInfo> getMonstersInfo() {
        return monstersInfo;
    }
    public List<WaveInfo> getWaveInfo() {
        return wavesInfo;
    }


}
