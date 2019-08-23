FILESEXTRAPATHS_prepend := "${THISDIR}/init-ifupdown-1.0:"

SRC_URI_append = " file://config"
SRC_URI_append = " file://rules"

do_install_append () {
    install -d ${D}${sysconfdir}/udev/rules.d
    install -m 0644 ${WORKDIR}/rules ${D}${sysconfdir}/udev/rules.d/95-ib-dhcp-en.rules
    install -m 0755 ${WORKDIR}/config ${D}${sysconfdir}/init.d/ifcfg-dhcp.sh
}
