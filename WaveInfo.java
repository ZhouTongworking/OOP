package WizardTD;

import java.util.List;

public class WaveInfo {
    private int duration;
    private float pre_wave_pause;
    private List<MonsterInfo> monstersInfo;

    public WaveInfo(int duration, float preWavePause, List<MonsterInfo> monstersInfo) {
        this.duration = duration;
        this.pre_wave_pause = preWavePause;
        this.monstersInfo = monstersInfo;
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
}
