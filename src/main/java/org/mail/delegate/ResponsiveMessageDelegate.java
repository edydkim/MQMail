package org.mail.delegate;

import org.mail.db.dto.MailInfoParamDto;

public interface ResponsiveMessageDelegate {

    String receive(MailInfoParamDto message);
}
