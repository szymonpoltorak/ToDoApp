export enum FormValidation {
    EMAIL_PATTERN = '^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$',
    EMAIL_VALUE = '',

    NAME_MIN_LENGTH = 3,
    NAME_MAX_LENGTH = 20,
    NAME_PATTERN = "[a-zA-ZżźćńółęąśŻŹĆĄŚĘŁÓŃ]+",
    NAME_VALUE = '',

    PASSWORD_PATTERN = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[:\\?\\.@!#:\\-_=+ ])[a-zA-Z0-9:\\?\\.@!#:\\-_=+ ]{8,20}$",
    PASSWORD_VALUE = ''
}
