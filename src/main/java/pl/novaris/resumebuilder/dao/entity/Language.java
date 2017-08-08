package pl.novaris.resumebuilder.dao.entity;

public class Language {
    private String name;
    private String level;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "Language{" +
                "name='" + name + '\'' +
                ", level='" + level + '\'' +
                '}';
    }
}
