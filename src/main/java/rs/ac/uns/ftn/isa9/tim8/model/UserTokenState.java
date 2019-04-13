package rs.ac.uns.ftn.isa9.tim8.model;

public class UserTokenState {
	
    private String accessToken;
    private Long expiresIn;
    private TipKorisnika tipKorisnika;

    public UserTokenState() {
        this.accessToken = null;
        this.expiresIn = null;
        this.tipKorisnika = null;
    }

    public UserTokenState(String accessToken, long expiresIn, TipKorisnika tipKor) {
        this.accessToken = accessToken;
        this.expiresIn = expiresIn;
        this.tipKorisnika = tipKor;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

	public TipKorisnika getTipKorisnika() {
		return tipKorisnika;
	}

	public void setTipKorisnika(TipKorisnika tipKorisnika) {
		this.tipKorisnika = tipKorisnika;
	}
    
    
}