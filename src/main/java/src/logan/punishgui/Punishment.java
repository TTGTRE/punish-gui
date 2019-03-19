package logan.punishgui;

class Punishment {

    private String reason;
    private long duration;

    public Punishment(String reason, long duration) {
        this.reason = reason;
        this.duration = duration;
    }

    public String getReason() {
        return reason;
    }

    public String getReadableDuration() {
        return TimeUtil.asReadableTime(duration);
    }

    public long getDuration() {
        return duration;
    }
}