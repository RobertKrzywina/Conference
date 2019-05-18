package pl.robert.app.views;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.*;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import pl.robert.app.lecture.domain.LectureFacade;
import pl.robert.app.lecture.domain.query.AlreadySubscribedLectureQueryDto;
import pl.robert.app.shared.GlobalAuthorizationEntryPoint;
import pl.robert.app.shared.NotificationService;
import pl.robert.app.user.domain.UserFacade;
import pl.robert.app.user.domain.query.UserQueryDto;

import java.util.List;

@SpringView(name = "profile")
@FieldDefaults(level = AccessLevel.PRIVATE)
class UserProfileView extends Composite implements View {

    UserFacade userFacade;
    LectureFacade lectureFacade;

    VerticalLayout root;

    public UserProfileView(UserFacade userFacade,
                           LectureFacade lectureFacade) {
        this.userFacade = userFacade;
        this.lectureFacade = lectureFacade;

        setupLayout();
        unauthorized();
        authorized();
        addHomepageHref();
    }

    private void setupLayout() {
        root = new VerticalLayout();
        root.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
        setCompositionRoot(root);
    }

    private void unauthorized() {
        if (!GlobalAuthorizationEntryPoint.isAuthorized()) {
            NotificationService.showErrorNotification("Zaloguj się aby zobaczyć informacje o profilu użytkownika");

            root.addComponent(new Label("Błąd 403: Odmowa dostępu"));
            addHomepageHref();
        }
    }

    private void authorized() {
        if (GlobalAuthorizationEntryPoint.isAuthorized()) {
            addHeader();
            addProfile();
        }
    }

    private void addHeader() {
        root.addComponent(new Label("Informacje o Twoim koncie"));
    }

    private void addProfile() {
        VerticalLayout layout = new VerticalLayout();

        UserQueryDto dto = userFacade.read();

        Label name = new Label("Twoje imię: " + dto.getName());
        Label email = new Label("Twój adres email: " + dto.getEmail());

        Button changeEmailBtn = new Button("Zmień email");
        changeEmailBtn.setStyleName("link");
        changeEmailBtn.addClickListener(e -> getUI().getNavigator().navigateTo("change-email"));

        layout.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        layout.addComponents(
                name,
                email,
                changeEmailBtn
        );

        root.addComponent(layout);
        addAlreadySubscribedLectures();
    }

    private void addAlreadySubscribedLectures() {
        List<AlreadySubscribedLectureQueryDto> alreadySubscribedLecturesSchema =
                lectureFacade.findAlreadySubscribedLectures();

        Grid<AlreadySubscribedLectureQueryDto> grid = new Grid<>();
        grid.setSizeFull();

        grid.setItems(alreadySubscribedLecturesSchema);
        grid.addColumn(AlreadySubscribedLectureQueryDto::getId).setCaption("Identyfikator");
        grid.addColumn(AlreadySubscribedLectureQueryDto::getName).setCaption("Nazwa");

        root.addComponents(
                new Label("Lista prelekcji na które zapisałeś się"),
                grid
        );
    }

    private void addHomepageHref() {
        Button button = new Button("Idź do strony głównej");
        button.setStyleName("link");
        button.addClickListener(e -> getUI().getNavigator().navigateTo("homepage"));

        root.addComponent(button);
    }
}