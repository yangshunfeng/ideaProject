package util;

import java.util.Random;

import model.Apply;
import model.Record;
import service.ApplyService;
import service.RecordService;

public class DataUtil {
    public static void main(String[] args) {
        Apply apply = new Apply();
        apply.introduction = "无";
        apply.vote_id = 1;
        for (int i = 4; i < 15; i++) {
            apply.is_accept = 1;
            apply.user_id = i;
            ApplyService.insert(apply);
        }
        Record record = new Record();
        record.vote_id = 1;
        record.introduction = "无";
        Random random = new Random();
        for (int i = 15; i <= 70; i++) {
            record.send_id = i;
            record.aim_id = random.nextInt(11) + 4;
            RecordService.insert(record);
        }
    }
}
