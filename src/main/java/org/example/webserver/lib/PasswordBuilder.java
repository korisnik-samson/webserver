package org.example.webserver.lib;

public class PasswordBuilder {
    public String generateNumbers() {
        String numbers = "0123456789";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int index = (int) (numbers.length() * Math.random());
            password.append(numbers.charAt(index));
        }

        return password.toString();
    }

    public String generateLetters() {
        String lower_letters = "abcdefghijklmnopqrstuvwxyz";
        String upper_letters = lower_letters.toUpperCase();

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int index = (int) (lower_letters.length() * Math.random());
            int u_index = (int) (upper_letters.length() * Math.random());

            password.append(lower_letters.charAt(index));
            password.append(lower_letters.charAt(u_index));
        }

        return password.toString();
    }

    public String generateSpecialCharacters() {
        String special_characters = "!@#$%^&*()_+";
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            int index = (int) (special_characters.length() * Math.random());
            password.append(special_characters.charAt(index));
        }

        return password.toString();
    }

    public String generatePassword() {
        return generateLetters() + generateNumbers() + generateSpecialCharacters();
    }
}
