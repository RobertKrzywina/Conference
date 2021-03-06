package pl.robert.app.user.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;

import pl.robert.app.user.domain.dto.CreateUserDto;
import pl.robert.app.user.domain.query.UserQueryDto;
import pl.robert.app.user.domain.exception.InvalidUserException;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
class UserService {

    UserRepository repository;
    UserFactory factory;

    void create(CreateUserDto dto) {
        repository.save(factory.create(dto));
    }

    UserQueryDto read(String name) {
        return repository.findUserByName(name)
                .map(User::query)
                .orElseThrow(() -> new InvalidUserException(InvalidUserException.CAUSE.NAME_NOT_EXISTS));
    }

    void update(String name, String email) {
        repository.findUserByName(name)
                .map(user -> {
                    user.setEmail(email);
                    return user;
                })
                .orElseThrow(() -> new InvalidUserException(InvalidUserException.CAUSE.NAME_NOT_EXISTS));
    }
}
