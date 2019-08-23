FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://ipmievd \
                   file://ipmievd.service \
                   file://ipmi.init.dev \
                   file://89-ipmi-i2c.rules"

FILES_${PN} =+ "${sysconfdir}/sysconfig/ipmievd"
FILES_${PN} =+ "${systemd_system_unitdir}/ipmievd.service"
FILES_${PN} =+ "${sysconfdir}/udev/scripts/ipmi.init.dev"

do_install_append() {
    install -d ${D}${sysconfdir}/sysconfig
    install -m 644 ${WORKDIR}/ipmievd ${D}${sysconfdir}/sysconfig
    install -d ${D}${systemd_system_unitdir}
    install -m 644 ${WORKDIR}/ipmievd.service ${D}${systemd_system_unitdir}
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 644 ${WORKDIR}/89-ipmi-i2c.rules ${D}${sysconfdir}/udev/rules.d
    install -d ${D}${sysconfdir}/udev/scripts
    install -m 755 ${WORKDIR}/ipmi.init.dev ${D}${sysconfdir}/udev/scripts
}
