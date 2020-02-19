package app.web.scout.util;

/**
 * Application constant values.
 * @author Luis
 *
 */
public class Constant {

	//TODO: Colocar como parámetros.
	public static final String HACHIKO_WS_URL = "http://www.ancla.edu.ec:8081/sisac/ws";
	public static final String AUTHORIZATION_TOKEN = "b@5jdc97a-*Nw@+q-)sWW[MUH6ZeU}hnhq*hD:;84*r+@J-g;!=7k(Yy8]e*V7^:";
	public static final int DIAS_PROYECTAR = 15;
	public static final int MINUTOS_ANTICIPACION = 30;

	public static final String DELETED_STATUS = "X";
	
	public static final double PORCENTAJE_BAJA_CALIFICACION = 50.00;

	// Token expiration control
	public static final int ACCESS_TOKEN_VALIDITY_SECONDS = 3600; // 1 hour
	public static final int REFRESH_TOKEN_VALIDITY_SECONDS = 5184000; // 60 days

	// Firebase constants
	
	public static final String GOOGLE_PUSH_SERVICES_CONFIG_FILE = "googleServiceAccountKey.json";
	public static final String GOOGLE_PUSH_SERVICES_DATABASE_URL = "https://ancla-mobile-app.firebaseio.com/";

	// Roles a gestionar.
	public static final String ROLE_ADMINISTRADOR = "ROLE_ADMINISTRADOR";
	public static final String ROLE_SUPERVISOR = "ROLE_SUPERVISOR";
	public static final String ROLE_DOCENTE = "ROLE_DOCENTE";
	public static final String ROLE_DIRECTIVO = "ROLE_DIRECTIVO";
	public static final String ROLE_ADMINISTRATIVO = "ROLE_ADMINISTRATIVO";
	public static final String ROLE_REPRESENTANTE = "ROLE_REPRESENTANTE";

	// Report formats
	public static final String REPORT_FORMAT_PDF = "PDF";
	public static final String REPORT_FORMAT_XLS = "XLS";
	
	public static final String SCHEDULE_REPORT_NAME = "reports/cita.jasper";
	public static final String GENERAL_REPORT_NAME = "reports/reporteGeneral.jasper";
	public static final String BY_SCORE_REPORT_NAME = "reports/reportePorPuntuacion.jasper";
	public static final String SCORE_REPORT_NAME = "reports/reportePuntuacion.jasper";
	
	public static final String DEFAULT_LOGO_NAME="logo.png";

	public static final String MOBILE_APP_NOTIFICATION_SOUND="default";
	public static final String MOBILE_APP_NOTIFICATION_ICON_NAME="ancla";
	public static final String MOBILE_APP_NOTIFICATION_ICON_COLOR="#001057";
	public static final String MOBILE_APP_NOTIFICATION_PRIORITY = "High";
	


}
