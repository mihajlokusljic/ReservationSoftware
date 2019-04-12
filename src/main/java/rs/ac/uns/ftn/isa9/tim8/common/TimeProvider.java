package rs.ac.uns.ftn.isa9.tim8.common;

import java.io.Serializable;
import java.util.Date;

import org.springframework.stereotype.Component;

@Component
public class TimeProvider implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6782126417912670801L;

	public Date now() {
		return new Date();
	}
}
