FILESEXTRAPATHS_prepend := "${THISDIR}/initramfs-framework:"

LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COREBASE}/meta/COPYING.MIT;md5=3da9cfbcb788c80a0384361b4de20420"

SRC_URI_append = " file://ibconfig"
SRC_URI_append = " file://nfsrootfs"

do_install() {
    install -d ${D}/init.d

    # base
    install -m 0755 ${WORKDIR}/init ${D}/init
    install -m 0755 ${WORKDIR}/nfsrootfs ${D}/init.d/85-nfsrootfs
    install -m 0755 ${WORKDIR}/rootfs ${D}/init.d/90-rootfs
    install -m 0755 ${WORKDIR}/finish ${D}/init.d/99-finish

	# exec
    install -m 0755 ${WORKDIR}/exec ${D}/init.d/89-exec

    # mdev
    install -m 0755 ${WORKDIR}/mdev ${D}/init.d/01-mdev

    # udev
    install -m 0755 ${WORKDIR}/udev ${D}/init.d/01-udev

    # e2fs
    install -m 0755 ${WORKDIR}/e2fs ${D}/init.d/10-e2fs

    # debug
    install -m 0755 ${WORKDIR}/debug ${D}/init.d/00-debug

    # lvm
    install -m 0755 ${WORKDIR}/lvm ${D}/init.d/09-lvm

    # Create device nodes expected by some kernels in initramfs
    # before even executing /init.
    #
    # MLNX: We don't create /dev/console because that causes issues
    # manipulating the cpio file when not su.
    #
    install -d ${D}/dev
#    mknod -m 622 ${D}/dev/console c 5 1

    install -m 0755 ${WORKDIR}/nfsrootfs ${D}/init.d/85-nfsrootfs

    install -d ${D}/exec.d
    install -m 0755 ${WORKDIR}/ibconfig ${D}/exec.d/01-ibconfig
}

FILES_initramfs-module-exec =+ "/exec.d/01-ibconfig"

RDEPENDS_initramfs-module-mdev = "${PN}-base"

SUMMARY_initramfs-module-nfsrootfs = "initramfs support for locating and mounting the root partition via nfs"
RDEPENDS_initramfs-module-nfsrootfs = "${PN}-base"
FILES_initramfs-module-nfsrootfs = "/init.d/85-nfsrootfs"
