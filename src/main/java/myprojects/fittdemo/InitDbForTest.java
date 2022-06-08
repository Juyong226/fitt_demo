package myprojects.fittdemo;

import lombok.RequiredArgsConstructor;
import myprojects.fittdemo.domain.*;
import myprojects.fittdemo.domain.Record;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class InitDbForTest {

    private final InitService initService;

    @PostConstruct
    public void init() {

        initService.initMemberAndRecord();
        initService.initMuscleCategories();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final EntityManager em;

        public void initMemberAndRecord() {
            for (int i=1; i<=9; i++) {
                String temp = String.valueOf(i);
                String name = "회원" + temp;
                String dateOfBirth = "199" + temp + "-0" + temp + "-15";
                String dateOfJoin = "202";
                if (i<6) {
                    dateOfJoin += "1-0" + temp + "-23";
                } else {
                    dateOfJoin += "0-0" + temp + "-10";
                }
                Member member = Member.create(name, LocalDate.parse(dateOfBirth), LocalDate.parse(dateOfJoin));
                em.persist(member);
                em.persist(Record.create(member));
            }
        }

        public void initMuscleCategories() {
            MuscleCategory back = MuscleCategory.create(TargetMuscle.BACK);
            em.persist(back);

            MuscleCategory chest = MuscleCategory.create(TargetMuscle.CHEST);
            em.persist(chest);

            MuscleCategory shoulders = MuscleCategory.create(TargetMuscle.SHOULDERS);
            em.persist(shoulders);

            MuscleCategory abs = MuscleCategory.create(TargetMuscle.ABS);
            em.persist(abs);

            MuscleCategory arms = MuscleCategory.create(TargetMuscle.ARMS);
            em.persist(arms);

            MuscleCategory hip = MuscleCategory.create(TargetMuscle.HIP);
            em.persist(hip);

            MuscleCategory thighs = MuscleCategory.create(TargetMuscle.THIGHS);
            em.persist(thighs);

            MuscleCategory calf = MuscleCategory.create(TargetMuscle.CALF);
            em.persist(calf);

            MuscleCategory upperBody = MuscleCategory.create(TargetMuscle.UPPER_BODY);
            em.persist(upperBody);

            MuscleCategory lowerBody = MuscleCategory.create(TargetMuscle.LOWER_BODY);
            em.persist(lowerBody);

            MuscleCategory frontMuscleChain = MuscleCategory.create(TargetMuscle.FRONT_MUSCLE_CHAIN);
            em.persist(frontMuscleChain);

            MuscleCategory rearMuscleChain = MuscleCategory.create(TargetMuscle.REAR_MUSCLE_CHAIN);
            em.persist(rearMuscleChain);

            Map<String, MuscleCategory> muscleCategories = new HashMap<>();
            muscleCategories.put("back", back);
            muscleCategories.put("chest", chest);
            muscleCategories.put("shoulders", shoulders);
            muscleCategories.put("abs", abs);
            muscleCategories.put("arms", arms);
            muscleCategories.put("hip", hip);
            muscleCategories.put("thighs", thighs);
            muscleCategories.put("calf", calf);
            muscleCategories.put("upperBody", upperBody);
            muscleCategories.put("lowerBody", lowerBody);
            muscleCategories.put("frontMuscleChain", frontMuscleChain);
            muscleCategories.put("rearMuscleChain", rearMuscleChain);

            initWorkouts(muscleCategories);
        }

        private void initWorkouts(Map<String, MuscleCategory> muscleCategories) {
            Workout latPullDown = Workout.create("렛 풀 다운");
            WorkoutMuscleCategory.create(latPullDown, muscleCategories.get("back"));
            WorkoutMuscleCategory.create(latPullDown, muscleCategories.get("upperBody"));
            WorkoutMuscleCategory.create(latPullDown, muscleCategories.get("rearMuscleChain"));
            em.persist(latPullDown);

            Workout barbellRow = Workout.create("바벨 로우");
            WorkoutMuscleCategory.create(barbellRow, muscleCategories.get("back"));
            WorkoutMuscleCategory.create(barbellRow, muscleCategories.get("upperBody"));
            WorkoutMuscleCategory.create(barbellRow, muscleCategories.get("rearMuscleChain"));
            em.persist(barbellRow);

            Workout benchPress = Workout.create("벤치 프레스");
            WorkoutMuscleCategory.create(benchPress, muscleCategories.get("chest"));
            WorkoutMuscleCategory.create(benchPress, muscleCategories.get("upperBody"));
            WorkoutMuscleCategory.create(benchPress, muscleCategories.get("frontMuscleChain"));
            em.persist(benchPress);

            Workout cableCrossOver = Workout.create("케이블 크로스 오버");
            WorkoutMuscleCategory.create(cableCrossOver, muscleCategories.get("chest"));
            WorkoutMuscleCategory.create(cableCrossOver, muscleCategories.get("upperBody"));
            WorkoutMuscleCategory.create(cableCrossOver, muscleCategories.get("frontMuscleChain"));
            em.persist(cableCrossOver);

            Workout shoulderPress = Workout.create("숄더 프레스");
            WorkoutMuscleCategory.create(shoulderPress, muscleCategories.get("shoulders"));
            WorkoutMuscleCategory.create(shoulderPress, muscleCategories.get("upperBody"));
            WorkoutMuscleCategory.create(shoulderPress, muscleCategories.get("frontMuscleChain"));
            em.persist(shoulderPress);

            Workout cableCrunch = Workout.create("케이블 크런치");
            WorkoutMuscleCategory.create(cableCrunch, muscleCategories.get("abs"));
            WorkoutMuscleCategory.create(cableCrunch, muscleCategories.get("upperBody"));
            WorkoutMuscleCategory.create(cableCrunch, muscleCategories.get("frontMuscleChain"));
            em.persist(cableCrunch);

            Workout cablePushDown = Workout.create("케이블 푸쉬 다운");
            WorkoutMuscleCategory.create(cablePushDown, muscleCategories.get("arms"));
            WorkoutMuscleCategory.create(cablePushDown, muscleCategories.get("upperBody"));
            em.persist(cablePushDown);

            Workout barbellCurl = Workout.create("바벨 컬");
            WorkoutMuscleCategory.create(barbellCurl, muscleCategories.get("arms"));
            WorkoutMuscleCategory.create(barbellCurl, muscleCategories.get("upperBody"));
            em.persist(barbellCurl);

            Workout hipThruster = Workout.create("힙 쓰러스터");
            WorkoutMuscleCategory.create(hipThruster, muscleCategories.get("hip"));
            WorkoutMuscleCategory.create(hipThruster, muscleCategories.get("lowerBody"));
            WorkoutMuscleCategory.create(hipThruster, muscleCategories.get("rearMuscleChain"));
            em.persist(hipThruster);

            Workout barbellSquat = Workout.create("바벨 스쿼트");
            WorkoutMuscleCategory.create(barbellSquat, muscleCategories.get("thighs"));
            WorkoutMuscleCategory.create(barbellSquat, muscleCategories.get("lowerBody"));
            WorkoutMuscleCategory.create(barbellSquat, muscleCategories.get("frontMuscleChain"));
            WorkoutMuscleCategory.create(barbellSquat, muscleCategories.get("rearMuscleChain"));
            em.persist(barbellSquat);

            Workout lunge = Workout.create("런지");
            WorkoutMuscleCategory.create(lunge, muscleCategories.get("thighs"));
            WorkoutMuscleCategory.create(lunge, muscleCategories.get("lowerBody"));
            WorkoutMuscleCategory.create(lunge, muscleCategories.get("frontMuscleChain"));
            WorkoutMuscleCategory.create(lunge, muscleCategories.get("rearMuscleChain"));
            em.persist(lunge);

            Workout legExtension = Workout.create("레그 익스텐션");
            WorkoutMuscleCategory.create(legExtension, muscleCategories.get("thighs"));
            WorkoutMuscleCategory.create(legExtension, muscleCategories.get("lowerBody"));
            WorkoutMuscleCategory.create(legExtension, muscleCategories.get("frontMuscleChain"));
            em.persist(legExtension);

            Workout calfRaise = Workout.create("카프 레이즈");
            WorkoutMuscleCategory.create(calfRaise, muscleCategories.get("calf"));
            WorkoutMuscleCategory.create(calfRaise, muscleCategories.get("lowerBody"));
            WorkoutMuscleCategory.create(calfRaise, muscleCategories.get("rearMuscleChain"));
            em.persist(calfRaise);

        }
    }
}
