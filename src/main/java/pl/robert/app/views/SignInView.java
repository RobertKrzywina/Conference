package pl.robert.app.views;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

import com.vaadin.ui.Label;
import com.vaadin.ui.Button;
import com.vaadin.server.Page;
import com.vaadin.ui.TextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Composite;
import com.vaadin.navigator.View;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.server.VaadinSession;
import com.vaadin.spring.annotation.SpringView;

import pl.robert.app.user.domain.UserFacade;
import pl.robert.app.shared.VaadinNotificationService;
import pl.robert.app.shared.ParameterizedException;
import pl.robert.app.shared.GlobalAuthorizationEntryPoint;

@SpringView(name = "login")
@FieldDefaults(level = AccessLevel.PRIVATE)
class SignInView extends Composite implements View {

    UserFacade facade;

    VerticalLayout root;

    public SignInView(UserFacade facade) {
        this.facade = facade;

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
            addHeader();
            addForm();
        }
    }

    private void addHeader() {
        root.addComponent(new Label("Logowanie"));
    }

    private void addForm() {
        HorizontalLayout formLayout = new HorizontalLayout();

        TextField name = new TextField("Imię");
        Button login = new Button("Zaloguj");

        login.addClickListener((clickEvent) -> {
            facade.login(name.getValue());
            Page.getCurrent().reload();
        });

        VaadinSession.getCurrent().setErrorHandler((handler) ->
            VaadinNotificationService.showErrorNotification(ParameterizedException.label)
        );

        formLayout.addComponents(
                name,
                login
        );

        root.addComponent(formLayout);
    }

    private void authorized() {
        if (GlobalAuthorizationEntryPoint.isAuthorized()) {
            VaadinNotificationService.showErrorNotification("Jesteś zalogowany, aby się wylogować przejdź do strony głównej");
            root.addComponents(new Label("Błąd 403: Odmowa dostępu"));
        }
    }

    private void addHomepageHref() {
        Button button = new Button("Idź do strony głównej");

        button.setStyleName("link");
        button.addClickListener(e -> getUI().getNavigator().navigateTo("homepage"));

        root.addComponent(button);
    }
}
