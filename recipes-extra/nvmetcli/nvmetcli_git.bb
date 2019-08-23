SUMMARY = "NVMe target configuration tool"

DESCRIPTION = "This contains the NVMe target admin tool nvmetcli. \
It can either be used interactively by invoking it without arguments, \
or it can be used to save, restore or clear the current NVMe target \
configuration."

INSANE_SKIP_${PN} += "installed-vs-shipped"
FILES_${PN} += "${libdir}/*"

S = "${WORKDIR}/git"

LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://COPYING;md5=1dece7821bf3fd70fe1309eaa37d52a2"

inherit systemd

SYSTEMD_SERVICE_${PN} = "nvmet.service"

RDEPENDS_${PN} = "python"

SRC_URI = "git://git.infradead.org/users/hch/nvmetcli.git"
SRCREV = "9d51ae651a1c39a83b0192fbbe1e400abf3a0409"

do_install_append () {
    install -d ${D}/${sbindir}
    install -m 0755 ${S}/nvmetcli ${D}/${sbindir}/

    install -d ${D}/${libdir}/python2.7/site-packages/nvmet/
    cp -r ${S}/nvmet/* ${D}/${libdir}/python2.7/site-packages/nvmet/

    install -d ${D}${systemd_system_unitdir}
    install -m 0644 ${S}/nvmet.service ${D}${systemd_system_unitdir}
}
