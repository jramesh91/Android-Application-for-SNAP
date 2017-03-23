    package teamj.application.appforsnaptest.view;

    /**
     * Created by Rakesh on 09-12-2016.
     */

    public class Weather {
        public Integer R_id;
        public String source;
        public String destination;
        public Weather(){
            super();
        }

        public Weather(Integer icon, String source, String destination) {
            super();
            this.R_id = icon;
            this.source= source;
            this.destination= destination;

        }
    }
