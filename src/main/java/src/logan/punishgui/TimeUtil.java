package logan.punishgui;

public class TimeUtil {

    public static long getTime(String timeString) {
        Pattern pattern = Pattern.compile("\\d+\\D{1}");
        Matcher matcher = pattern.matcher(timeString);

        long timeout = 0;
        while (matcher.find()) {

            String match = timeString.substring(matcher.start(), matcher.end());

            int time = Integer.parseInt(match.substring(0, match.length() - 1));
            char timeUnit = match.charAt(match.length() - 1);

            switch (timeUnit) {
                case 'd':
                    timeout += TimeUnit.DAYS.toMillis(time);
                    break;
                case 'h':
                    timeout += TimeUnit.HOURS.toMillis(time);
                    break;
                case 'm':
                    timeout += TimeUnit.MINUTES.toMillis(time);
                    break;
                case 's':
                    timeout += TimeUnit.SECONDS.toMillis(time);
                    break;
                default:
                    break;
            }

        }

        return timeout;
    }

    public static String asReadableTime(long millis) {

        long x = millis / 1000;
        long seconds = x % 60;
        x /= 60;
        long minutes = x % 60;
        x /= 60;
        long hours = x % 24;
        x /= 24;
        long days = x;

        StringBuilder builder = new StringBuilder();

        if (days > 0) {
            builder.append(days).append(" days ");
        }
        if (hours > 0) {
            builder.append(hours).append(" hours ");
        }
        if (minutes > 0) {
            builder.append(minutes).append(" minutes ");
        }
        if (seconds > 0) {
            builder.append(seconds).append(" seconds ");
        }

        return builder.toString().trim();
    }
}