package pl.robert.app.user.domain;

import lombok.AllArgsConstructor;

import org.apache.logging.log4j.util.Strings;

import pl.robert.app.user.domain.dto.CreateUserDto;
import pl.robert.app.user.domain.exception.InvalidUserException;

import static pl.robert.app.shared.Constants.User.COL_MIN_LENGTH_NAME;
import static pl.robert.app.shared.Constants.User.COL_MAX_LENGTH_NAME;
import static pl.robert.app.shared.Constants.User.EMAIL_REGEX;

@AllArgsConstructor
class UserValidator {

    UserRepository repository;

    void checkInputDto(CreateUserDto dto) {
        InvalidUserException.CAUSE cause = null;

        if (Strings.isBlank(dto.getName())) {
            cause = InvalidUserException.CAUSE.BLANK_NAME;
        } else if (dto.getName().length() < COL_MIN_LENGTH_NAME || dto.getName().length() > COL_MAX_LENGTH_NAME) {
            cause = InvalidUserException.CAUSE.NAME_LENGTH;
        } else if (repository.findUserByName(dto.getName()) != null) {
            cause = InvalidUserException.CAUSE.NAME_EXISTS;
        }

        if (cause != null) {
            throw new InvalidUserException(cause);
        }

        validateEmail(dto.getEmail());
    }

    void checkInputName(String name) throws InvalidUserException {
        InvalidUserException.CAUSE cause = null;

        if (Strings.isBlank(name)) {
            cause = InvalidUserException.CAUSE.BLANK_NAME;
        } else if (repository.findUserByName(name) == null) {
            cause = InvalidUserException.CAUSE.NAME_NOT_EXISTS;
        }

        if (cause != null) {
            throw new InvalidUserException(cause);
        }
    }

    void checkInputEmail(String email) throws InvalidUserException {
        validateEmail(email);
    }

    private void validateEmail(String email) {
        InvalidUserException.CAUSE cause = null;

        if (Strings.isBlank(email)) {
            cause = InvalidUserException.CAUSE.BLANK_EMAIL;
        } else if (!EMAIL_REGEX.matcher(email).find()) {
            cause = InvalidUserException.CAUSE.EMAIL_FORMAT;
        }

        if (cause != null) {
            throw new InvalidUserException(cause);
        }
    }
}
