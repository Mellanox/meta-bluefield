FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

inherit systemd

SRC_URI_append = "file://snmpd.conf"

FILES_${PN} =+ "${sysconfdir}/snmp/snmpd.conf"

do_install_append() {
    install -d ${D}${sysconfdir}/snmp
    install -d ${D}${systemd_system_unitdir}
    install -m 644 ${WORKDIR}/snmpd.conf ${D}${sysconfdir}/snmp
    install -m 644 ${WORKDIR}/snmpd.service ${D}${systemd_system_unitdir}
}
