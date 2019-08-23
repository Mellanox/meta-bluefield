FILESEXTRAPATHS_prepend := "${THISDIR}/files:"

SRC_URI_append = " file://service"

FILES_${PN} += "\
                ${sbindir}/service \"

do_install_append() {
    install -d ${D}${sbindir}
    install -m 0755 ${WORKDIR}/service ${D}${sbindir}/
}
