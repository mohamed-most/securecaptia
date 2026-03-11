
CREATE SCHEMA IF NOT EXISTS securecapita;

-- This extension is required for gen_random_uuid() to work
CREATE EXTENSION IF NOT EXISTS "pgcrypto";

SET search_path TO securecapita;

DROP TABLE IF EXISTS UserRoles CASCADE;
DROP TABLE IF EXISTS UserEvents CASCADE;
DROP TABLE IF EXISTS AccountVerifications CASCADE;
DROP TABLE IF EXISTS ResetPasswordVerifications CASCADE;
DROP TABLE IF EXISTS TwoFactorVerifications CASCADE;
DROP TABLE IF EXISTS Users CASCADE;
DROP TABLE IF EXISTS Roles CASCADE;
DROP TABLE IF EXISTS Events CASCADE;

CREATE TABLE Users
(
    user_id    UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name  VARCHAR(50) NOT NULL,
    email      VARCHAR(100) NOT NULL,
    password   VARCHAR(255) DEFAULT NULL,
    address    VARCHAR(255) DEFAULT NULL,
    phone      VARCHAR(30) DEFAULT NULL,
    title      VARCHAR(50) DEFAULT NULL,
    bio        VARCHAR(255) DEFAULT NULL,
    enabled    BOOLEAN DEFAULT FALSE,
    non_locked BOOLEAN DEFAULT TRUE,
    using_mfa  BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    image_url  VARCHAR(255) DEFAULT 'https://cdn-icons-png.flaticon.com/512/149/149071.png',
    CONSTRAINT UQ_Users_Email UNIQUE (email)
);

CREATE TABLE Roles
(
    role_id    UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    name       VARCHAR(50) NOT NULL,
    permission VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Roles_Name UNIQUE (name)
);

CREATE TABLE UserRoles
(
    user_role_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id      UUID NOT NULL,
    role_id      UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (role_id) REFERENCES Roles (role_id) ON DELETE RESTRICT ON UPDATE CASCADE,
    CONSTRAINT UQ_UserRoles_User_Id UNIQUE (user_id)
);

CREATE TABLE Events
(
    event_id    UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    type        VARCHAR(50) NOT NULL CHECK(type IN ('LOGIN_ATTEMPT', 'LOGIN_ATTEMPT_FAILURE', 'LOGIN_ATTEMPT_SUCCESS', 'PROFILE_UPDATE', 'PROFILE_PICTURE_UPDATE', 'ROLE_UPDATE', 'ACCOUNT_SETTINGS_UPDATE', 'PASSWORD_UPDATE', 'MFA_UPDATE')),
    description VARCHAR(255) NOT NULL,
    CONSTRAINT UQ_Events_Type UNIQUE (type)
);

CREATE TABLE UserEvents
(
    user_event_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id       UUID NOT NULL,
    event_id      UUID NOT NULL,
    device        VARCHAR(100) DEFAULT NULL,
    ip_address    VARCHAR(100) DEFAULT NULL,
    created_at    TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (event_id) REFERENCES Events (event_id) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE AccountVerifications
(
    account_verification_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id                UUID NOT NULL,
    url                    VARCHAR(255) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_AccountVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_AccountVerifications_Url UNIQUE (url)
);

CREATE TABLE ResetPasswordVerifications
(
    reset_password_verification_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id                        UUID NOT NULL,
    url                            VARCHAR(255) NOT NULL,
    expiration_date                TIMESTAMPTZ NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_ResetPasswordVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_ResetPasswordVerifications_Url UNIQUE (url)
);

CREATE TABLE TwoFactorVerifications
(
    two_factor_verification_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_id                    UUID NOT NULL,
    code                       VARCHAR(10) NOT NULL,
    expiration_date            TIMESTAMPTZ NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (user_id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT UQ_TwoFactorVerifications_User_Id UNIQUE (user_id),
    CONSTRAINT UQ_TwoFactorVerifications_Code UNIQUE (code)
);