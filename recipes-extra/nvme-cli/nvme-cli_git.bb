SUMMARY = "nvme-cli utility"

DESCRIPTION = "NVM-Express user space tooling for Linux."

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=8264535c0c4e9c6c335635c4026a8022"

inherit pkgconfig

SRC_URI = "git://github.com/linux-nvme/nvme-cli.git;protocol=https"
SRCREV = "642d426faf8a67ed01e90f7c35c0d967f8cc52a3"

SRC_URI[md5sum] = "b42f1d2a8a51b127468db16dcf06f418"
SRC_URI[sha256sum] = "5ad72ff8b585ff46be359927b41888809bef85b943873da9899379f00453e0b9"

# This is required to get libuuid.
DEPENDS_append = " util-linux"

do_install_append () {
        install -d ${D}/${bindir}
        install ${S}/nvme ${D}/${bindir}/nvme
}

S = "${WORKDIR}/git"
