package pl.robert.app.lecture.domain;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

import pl.robert.app.user.domain.UserFacade;

@Configuration
class LectureConfiguration {

    @Bean
    LectureFacade lectureFacade(LectureRepository repository,
                                UserFacade userFacade) {
        return new LectureFacade(new LectureService(repository, userFacade));
    }
}