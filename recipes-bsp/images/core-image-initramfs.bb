SUMMARY = "A small image intended as the default initramfs. \
           This image is used to install poky or debug/test \
           a system.  It is NOT intended to be used as a \
           root file system.  Use the full or full-dev recipes \
           for your full file system."

PACKAGE_EXCLUDE_pn-core-image-initramfs = "systemd mesa-megadriver acl-dev attr-dev base-files-dev base-passwd-dev bash-dev bison-dev coreutils-dev cracklib-dev db-dev diffutils-dev flex-dev gawk-dev ncurses-dev perl-dev sed-dev shadow-dev shadow-securetty-dev"

PACKAGE_INSTALL = "${VIRTUAL-RUNTIME_base-utils} base-passwd ${ROOTFS_BOOTSTRAP_INSTALL}"

export IMAGE_BASENAME = "core-image-initramfs"

IMAGE_LINGUAS = ""

LICENSE = "MIT"

IMAGE_FSTYPES = "${INITRAMFS_FSTYPES}"

inherit image

IMAGE_ROOTFS_SIZE = "8192"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

COMPATIBLE_HOST = "aarch64.*-linux"

VIRTUAL-RUNTIME_base-utils += "${SMALL_INSTALL}"
VIRTUAL-RUNTIME_init_manager = "sysvinit"
VIRTUAL-RUNTIME_initscripts = "initscripts"

IMAGE_FSTYPES = "cpio cpio.xz"

# Add support for gdb/strace by default.
# IMAGE_FEATURES += "tools-debug"

export SMALL_INSTALL

SMALL_INSTALL_append = " sysvinit sysvinit-inittab"

# Packages authored by the BlueField software platform group.
SMALL_INSTALL_append = " mlxbf-bootctl mlnx-ofa-kernel mlnx-ofa-kernel-openibd"

# The Linaro image provided a few packages above and beyond the Poky minimal
# set; let's keep them for now.  We can always prune them later if need be.
SMALL_INSTALL_append = " bash procps openssh openssh-sftp openssh-sftp-server openssh-sshd"

# Include the drivers for the BlueField hardware
SMALL_INSTALL_append = " bluefield-edac gpio-mlxbf i2c-mlx mlx-bootctl"
SMALL_INSTALL_append = " mlxbf-livefish tmfifo mlx-l3cache mlx-trio"
SMALL_INSTALL_append = " ipmb-dev-int mlxbf-oob"

# Required for system provisioning
SMALL_INSTALL_append = " mft mft-oem kernel-mft"

# Useful utilities
SMALL_INSTALL_append = " i2c-tools parted e2fsprogs-mke2fs dosfstools \
util-linux gzip xz bfscripts bfversion ethtool pciutils \
lshw hwloc util-linux-lscpu rsync i2c-tools-misc \
perl-module-posix perl-module-file-basename perl-module-constant \
kernel-module-eeprom kernel-module-at24 kernel-module-at25 \
kernel-module-ee1004 kernel-module-8021q kernel-module-garp \
kernel-module-mrp kernel-module-stp kernel-module-llc vlan \
initscripts init-ifupdown busybox busybox-syslog busybox-udhcpc \
module-init-tools modutils-initscripts binutils"

# Use the "real" tar utility instead of the busybox version.
SMALL_INSTALL_append = " tar"

# File systems
SMALL_INSTALL_append = " xfsprogs xfsdump"

# UEFI related packages
SMALL_INSTALL_append = " grub efibootmgr efitools"

# Include PKA software
SMALL_INSTALL_append = " pka-mlxbf libpka1 libpka-tests \
libpka-openssl-engine libpka-doc"

fakeroot do_cleanup_rootfs () {

         # Don't check keys when starting the ssh server; we just provide
         # the RSA key and no other, for now.  Enable the HostKey line for
         # RSA so that sshd doesn't use its defaults to look for all the ones
         # we're not bothering to provide.
         sed -i -e '/check_keys$/d' ${IMAGE_ROOTFS}/etc/init.d/sshd
         sed -i -e '/host_rsa_key/s/#//' ${IMAGE_ROOTFS}/etc/ssh/sshd_config

         # Pre-created ssh host keys to keep initramfs startup quicker.
         install -m 0600 ${FILE_DIRNAME}/ssh_host_rsa_key \
           ${IMAGE_ROOTFS}/etc/ssh
         install -m 0644 ${FILE_DIRNAME}/ssh_host_rsa_key.pub \
           ${IMAGE_ROOTFS}/etc/ssh

         # Don't mount a new tmpfs on /var/volatile.
         sed -i -e '/var.volatile/d' ${IMAGE_ROOTFS}/etc/fstab

         # Don't mount the boot partition.
         sed -i -e 's/^\/dev\/mmcblk0p1/#&/' ${IMAGE_ROOTFS}/etc/fstab

         # Directory normally created by populate-volatile.sh
         mkdir ${IMAGE_ROOTFS}/var/volatile/log

         # Disable services that we don't need in the initramfs.
         for i in S15mountnfs.sh K21nfscommon S19nfscommon S31umountnfs.sh S03rng-tools K30rng-tools S20nfsserver K20nfsserver; do
             rm -f ${IMAGE_ROOTFS}/etc/rc?.d/$i
         done

         # Symlink for /init; only used when we get our root from initramfs.
         ln -s sbin/init ${IMAGE_ROOTFS}/init

         # We do NOT want persistent naming enabled for initramfs because
         # it interferes with the /etc/network/interfaces init.
         rm -rf ${IMAGE_ROOTFS}${systemd_unitdir}/network
}

IMAGE_PREPROCESS_COMMAND += "do_cleanup_rootfs; "

fakeroot do_update_rootfs_bluefield() {
  if [ ! -z "${MLNX_BLUEFIELD_FW_PATH}" ]; then
    if [ -d ${MLNX_BLUEFIELD_FW_PATH} ]; then
      install -d ${IMAGE_ROOTFS}/lib/firmware/mellanox
      cp ${MLNX_BLUEFIELD_FW_PATH}/* ${IMAGE_ROOTFS}/lib/firmware/mellanox
    fi
  fi
}

ROOTFS_POSTPROCESS_COMMAND += " do_update_rootfs_bluefield; "
