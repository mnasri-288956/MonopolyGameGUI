import java.io.*;
import java.util.*;

public class MonopolyGame {

    private int playerMoney = 1500;
    private int playerPosition = 0;

    // ---- Added fields for houses & hotel ----
    private int houses = 0;
    private boolean hotel = false;
    private int rentPrice = 50; // base rent example value

    // ---- Core game logic ----
    public int rollDice() {
        int roll = (int)(Math.random() * 6) + 1;
        playerPosition = (playerPosition + roll) % 40; // wrap around 40 spaces
        return roll;
    }

    public int getRent() {
        int base = rentPrice;
        if (hotel) return base * 5;
        return base + (houses * base / 2);
    }

    public void buyProperty() {
        if (playerMoney >= 200) {
            playerMoney -= 200;
            System.out.println("Property bought! Remaining: $" + playerMoney);
        } else {
            System.out.println("Not enough money!");
        }
    }

    public void buildHouse() {
        if (houses < 4 && !hotel) houses++;
        else if (houses == 4 && !hotel) { hotel = true; houses = 0; }
    }

    // ---- GUI helper methods ----
    public void resetForGUI() {
        playerMoney = 1500;
        playerPosition = 0;
    }

    public int guiRoll() {
        return rollDice();
    }

    public String guiBuyProperty() {
        if (playerMoney >= 200) {
            playerMoney -= 200;
            return "You bought a property. Remaining: $" + playerMoney;
        } else {
            return "Not enough money!";
        }
    }

    // ---- Save / Load ----
    public void reset() {
        playerMoney = 1500;
        playerPosition = 0;
        houses = 0;
        hotel = false;
    }

    public String toSaveString() {
        return playerMoney + "," + playerPosition + "," + houses + "," + hotel;
    }

    public void loadFromString(String s) {
        String[] parts = s.split(",");
        playerMoney = Integer.parseInt(parts[0]);
        playerPosition = Integer.parseInt(parts[1]);
        houses = (parts.length > 2) ? Integer.parseInt(parts[2]) : 0;
        hotel = (parts.length > 3) && Boolean.parseBoolean(parts[3]);
    }

    public void saveToFile(File f) throws Exception {
        try (PrintWriter out = new PrintWriter(f)) {
            out.println(toSaveString());
        }
    }

    public void loadFromFile(File f) throws Exception {
        try (Scanner sc = new Scanner(f)) {
            if (sc.hasNextLine()) loadFromString(sc.nextLine());
        }
    }

    // ---- Chance and Community Chest ----
    private String drawChance() {
        String[] cards = {
                "Advance to GO (Collect $200)",
                "Bank pays you dividend of $50",
                "Go back 3 spaces",
                "Pay poor tax of $15",
                "Your building loan matures – Collect $150"
        };
        int i = (int)(Math.random() * cards.length);
        return cards[i];
    }

    private String drawCommunityChest() {
        String[] cards = {
                "Doctor's fees – Pay $50",
                "From sale of stock you get $45",
                "Go to Jail – Do not pass GO",
                "Life insurance matures – Collect $100",
                "Receive $25 consultancy fee"
        };
        int i = (int)(Math.random() * cards.length);
        return cards[i];
    }

    public String getChanceCard() { return drawChance(); }
    public String getCommunityCard() { return drawCommunityChest(); }

    // ---- Getters ----
    public int getMoney() { return playerMoney; }
    public int getPosition() { return playerPosition; }
    public int getHouses() { return houses; }
    public boolean hasHotel() { return hotel; }
}
