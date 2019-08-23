SUMMARY = "Install BlueField scripts"
LICENSE = "BSD"

LIC_FILES_CHKSUM = \
"file://${WORKDIR}/bfbootmgr.sh;beginline=3;endline=28;md5=4efe07f3faa671afe72da6e577263023 \
 file://${WORKDIR}/bfcpu-freq.sh;beginline=3;endline=28;md5=4efe07f3faa671afe72da6e577263023 \
 file://${WORKDIR}/bfdracut.sh;beginline=3;endline=28;md5=af7c2555086a4dea14179634e1aff906 \
 file://${WORKDIR}/bffamily.sh;beginline=3;endline=28;md5=af7c2555086a4dea14179634e1aff906 \
 file://${WORKDIR}/bfinst.sh;beginline=3;endline=28;md5=4efe07f3faa671afe72da6e577263023 \
 file://${WORKDIR}/bfmisc.sh;beginline=3;endline=28;md5=5ade74e0d0cbf2a0dce277a73b1c09ff \
 file://${WORKDIR}/bfpart.sh;beginline=3;endline=28;md5=4efe07f3faa671afe72da6e577263023 \
 file://${WORKDIR}/bfpxe.sh;beginline=3;endline=28;md5=4efe07f3faa671afe72da6e577263023 \
 file://${WORKDIR}/bfrec.sh;beginline=3;endline=28;md5=4efe07f3faa671afe72da6e577263023 \
 file://${WORKDIR}/bfsbkeys.sh;beginline=3;endline=28;md5=5ade74e0d0cbf2a0dce277a73b1c09ff \
 file://${WORKDIR}/bfvcheck.sh;beginline=3;endline=28;md5=af7c2555086a4dea14179634e1aff906 \
 file://${WORKDIR}/bfver.sh;beginline=3;endline=28;md5=4efe07f3faa671afe72da6e577263023"

inherit allarch systemd bf_versioned

DIRFILES = "1"

RDEPENDS_${PN} = "mlxbf-bootctl mlxbf-bootimages bash"

SYSTEMD_PACKAGES = "${PN}"

SYSTEMD_SERVICE_${PN} = "bfvcheck.service"

SRC_URI = "file://bfbootmgr.sh \
           file://bfcpu-freq.sh \
           file://bfdracut.sh \
           file://bffamily.sh \
           file://bfinst.sh \
           file://bfmisc.sh \
           file://bfpart.sh \
           file://bfpxe.sh \
           file://bfrec.sh \
           file://bfsbkeys.sh \
           file://bfvcheck.service \
           file://bfvcheck.sh \
           file://bfver.sh \
           file://build-bfb \
           file://mlx-mkbfb"

do_install () {
   install -d ${D}/opt/mellanox/scripts
   ln -s /opt/mellanox ${D}/opt/mlnx
   install -m 0755 ${WORKDIR}/bfbootmgr.sh ${D}/opt/mellanox/scripts/bfbootmgr
   install -m 0755 ${WORKDIR}/bfcpu-freq.sh ${D}/opt/mellanox/scripts/bfcpu-freq
   install -m 0755 ${WORKDIR}/bfdracut.sh ${D}/opt/mellanox/scripts/bfdracut
   install -m 0755 ${WORKDIR}/bfinst.sh ${D}/opt/mellanox/scripts/bfinst
   install -m 0755 ${WORKDIR}/bfpart.sh ${D}/opt/mellanox/scripts/bfpart
   install -m 0755 ${WORKDIR}/bfpxe.sh ${D}/opt/mellanox/scripts/bfpxe
   install -m 0755 ${WORKDIR}/bfrec.sh ${D}/opt/mellanox/scripts/bfrec
   install -m 0755 ${WORKDIR}/bfver.sh ${D}/opt/mellanox/scripts/bfver
   install -m 0755 ${WORKDIR}/mlx-mkbfb ${D}/opt/mellanox/scripts/mlx-mkbfb
   install -m 0755 ${WORKDIR}/build-bfb ${D}/opt/mellanox/scripts/build-bfb
   install -m 0755 ${WORKDIR}/bfvcheck.sh ${D}/opt/mellanox/scripts/bfvcheck
   install -m 0755 ${WORKDIR}/bffamily.sh ${D}/opt/mellanox/scripts/bffamily
   install -m 0755 ${WORKDIR}/bfsbkeys.sh ${D}/opt/mellanox/scripts/bfsbkeys
   install -m 0755 ${WORKDIR}/bfmisc.sh ${D}/opt/mellanox/scripts/bfmisc

   install -d ${D}${systemd_system_unitdir}
   install -m 0644 ${WORKDIR}/bfvcheck.service ${D}${systemd_system_unitdir}/bfvcheck.service
}

PACKAGES = "${PN}"

FILES_${PN} = "/"

