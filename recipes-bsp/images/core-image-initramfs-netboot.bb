SUMMARY = "A small image capable of netbooting a device."

require recipes-core/images/core-image-minimal-initramfs.bb

export IMAGE_BASENAME = "core-image-initramfs-netboot"
IMAGE_FSTYPES = "cpio.lzma"

COMPATIBLE_HOST = "aarch64.*-linux"

INITRAMFS_SCRIPTS ?= "\
                      initramfs-framework-base \
                      initramfs-module-exec \
                      initramfs-module-udev \
                      initramfs-module-nfsrootfs \
                      initramfs-module-debug \
                     "

# Replace 'rootfs' module, default module which mounts the rootfs,
# since 'nfsrootfs' module is installed instead.
BAD_RECOMMENDATIONS += "initramfs-module-rootfs"

# Useful utilities
UTILS_INSTALL = " util-linux dhcp-client nfs-utils"

# Packages authored by the BlueField software platform group.
MLNX_OFA_KERNEL_INSTALL = " mlnx-ofa-kernel"

PACKAGE_INSTALL = "\
                   ${UTILS_INSTALL} \
                   ${MLNX_OFA_KERNEL_INSTALL} \
                   ${INITRAMFS_SCRIPTS} \
                  "

fakeroot do_cleanup_rootfs () {

    # Cleanup device nodes
    rm -fr ${IMAGE_ROOTFS}/dev

    # Cleanup the boot file system; this contains images which
    # increases the size of the initramfs image and are useless
    # when netbooting the device.
    [ -d ${IMAGE_ROOTFS}/boot ] && rm -fr ${IMAGE_ROOTFS}/boot

    # Cleanup media file system
    rm -fr ${IMAGE_ROOTFS}/media
}

IMAGE_PREPROCESS_COMMAND += "do_cleanup_rootfs; "
