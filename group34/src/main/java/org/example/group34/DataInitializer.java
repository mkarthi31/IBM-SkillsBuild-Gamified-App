package org.example.group34;

import org.example.group34.model.AchievementBadge;
import org.example.group34.repo.AchievementBadgeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AchievementBadgeRepository badgeRepository;

    public DataInitializer(AchievementBadgeRepository badgeRepository) {
        this.badgeRepository = badgeRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if(badgeRepository.count() == 0) {
            AchievementBadge badge1 = new AchievementBadge();
            badge1.setName("First Steps");
            badge1.setDescription("Complete 1 course");
            badge1.setImageColor("first_steps_color.png");
            badge1.setImageGrey("first_steps_grey.png");
            badgeRepository.save(badge1);

            AchievementBadge badge2 = new AchievementBadge();
            badge2.setName("Explorer");
            badge2.setDescription("Complete 5 courses");
            badge2.setImageColor("explorer_color.png");
            badge2.setImageGrey("explorer_grey.png");
            badgeRepository.save(badge2);

            AchievementBadge badge3 = new AchievementBadge();
            badge3.setName("Expert Learner");
            badge3.setDescription("Complete 10 courses");
            badge3.setImageColor("expert_learner_color.png");
            badge3.setImageGrey("expert_learner_grey.png");
            badgeRepository.save(badge3);

            AchievementBadge badge4 = new AchievementBadge();
            badge4.setName("Master of SkillsBuild");
            badge4.setDescription("Complete 20 courses");
            badge4.setImageColor("master_color.png");
            badge4.setImageGrey("master_grey.png");
            badgeRepository.save(badge4);

            AchievementBadge badge5 = new AchievementBadge();
            badge5.setName("Full Completionist");
            badge5.setDescription("Complete all available courses");
            badge5.setImageColor("completionist_color.png");
            badge5.setImageGrey("completionist_grey.png");
            badgeRepository.save(badge5);

            AchievementBadge badge6 = new AchievementBadge();
            badge6.setName("Consistency Champ");
            badge6.setDescription("Maintain a 7-day learning streak");
            badge6.setImageColor("consistency_champ_color.png");
            badge6.setImageGrey("consistency_champ_grey.png");
            badgeRepository.save(badge6);

            AchievementBadge badge7 = new AchievementBadge();
            badge7.setName("Learning Streak Pro");
            badge7.setDescription("Maintain a 30-day learning streak");
            badge7.setImageColor("streak_pro_color.png");
            badge7.setImageGrey("streak_pro_grey.png");
            badgeRepository.save(badge7);
        }
    }
}
