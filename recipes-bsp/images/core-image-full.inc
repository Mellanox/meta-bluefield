IMAGE_FSTYPES = "tar.xz ext4.xz"

# Packages authored by the BlueField software platform group.
IMAGE_INSTALL_append = " mlxbf-bootctl"

# openipmi support
IMAGE_INSTALL_append = " openipmi ipmitool"

# An administration shell for storage targets
IMAGE_INSTALL_append = " targetcli"

# Add additional Yocto-supported libraries not otherwise included.
IMAGE_INSTALL_append = " rpm libxml2-python kernel-dev libunwind grub \
  libnl kernel-modules os-release systemd boost libstdc++ kexec-tools vim \
  binutils-dev kernel-module-eeprom"

IMAGE_INSTALL_append = " python-netifaces python-ipaddress"

IMAGE_INSTALL_append = " perf"

# Supported by meta-openembedded
IMAGE_INSTALL_append = " numactl sysklogd tcpdump ifenslave"

# MLNX_OFED packages
IMAGE_INSTALL_append = " mlnx-ofa-kernel"
IMAGE_INSTALL_append = " mlnx-ofa-kernel-openibd"

# If you want to blacklist any of the mlnx-ofa-kernel modules
# uncomment this line.  Edit the mlnx_blacklist.conf file in the
# mlnx-ofa-kernel recipe directory.
IMAGE_INSTALL_append = " mlnx-ofa-kernel-blacklist"

IMAGE_INSTALL_append = " ofed-scripts"
IMAGE_INSTALL_append = " edac-utils"
IMAGE_INSTALL_append = " rdma-core"
IMAGE_INSTALL_append = " perftest"
IMAGE_INSTALL_append = " mlnx-iproute2"
IMAGE_INSTALL_append = " infiniband-diags opensm"

# Mellanox Firmware Tools
IMAGE_INSTALL_append = " mstflint mft mft-oem kernel-mft"

# NVDIMM support
# IMAGE_INSTALL_append = " libpmem libpmemblk libpmemlog libpmemobj libpmempool libvmem libvmmalloc"
IMAGE_INSTALL_append = " i2c-tools"
IMAGE_INSTALL_append = " ndctl"

# Utilities
IMAGE_INSTALL_append = " usecpu calibrator idlestat fwts acpica gzip xz iperf3 \
fio sysbench ethtool lshw nvme-cli hwloc util-linux-lscpu inetutils \
inetutils-tftp inetutils-tftpd inetutils-ftp inetutils-ftpd \
inetutils-telnet inetutils-telnetd vlan iperf2 sshpass util-linux-blkid \
i2c-tools-misc perl-module-posix perl-module-file-basename \
perl-module-constant lmbench nvmetcli dosfstools pciutils acpid acpitool"

# Include the drivers for the BlueField hardware
IMAGE_INSTALL_append = " bluefield-edac gpio-mlxbf i2c-mlx mlx-bootctl"
IMAGE_INSTALL_append = " mlx-pmc mlx-cpld mlxbf-livefish tmfifo"
IMAGE_INSTALL_append = " mlx-l3cache mlx-trio ipmb-dev-int mlxbf-oob"

IMAGE_INSTALL_append = " bfversion bfscripts"

# File systems
IMAGE_INSTALL_append = " xfsprogs xfsdump"

# UEFI related packages
IMAGE_INSTALL_append = " efibootmgr efitools"

IMAGE_INSTALL_append = " dhcp-client nfs-utils dracut"

IMAGE_INSTALL_append = " openvswitch iproute2-tc iproute2-ifstat \
iproute2-lnstat iproute2-nstat iproute2-rtacct iproute2-ss iproute2-genl \
strongswan net-snmp mlnx-snmp-subagent"

IMAGE_INSTALL_append = " e2fsprogs-e2fsck e2fsprogs-mke2fs \
e2fsprogs-tune2fs e2fsprogs-badblocks e2fsprogs-resize2fs libcomerr \
libss libe2p libext2fs"

IMAGE_INSTALL_append = " pka-mlxbf libpka1 libpka-tests libpka-openssl-engine libpka-doc"

IMAGE_INSTALL_append = " lmsensors-libsensors lmsensors-sensors \
lmsensors-sensord lmsensors-fancontrol lmsensors-sensorsdetect \
lmsensors-sensorsconfconvert lmsensors-pwmconfig"

IMAGE_INSTALL_append = " openssh-sftp openssh-sftp-server"

IMAGE_INSTALL_append = " smartmontools mmc-utils"

KERNEL_MODULE_AUTOLOAD += "openvswitch"

POPULATE_SDK_POST_HOST_COMMAND += " update_sdk_bluefield; "

fakeroot update_sdk_bluefield() {
  baseoutpath=${SDK_OUTPUT}/${SDKPATH}
  rm -rf $baseoutpath/mellanox
  install -d $baseoutpath/mellanox/SRPMS
  install -d $baseoutpath/mellanox/RPMS
  if [[ ! -z "${MLNX_OFED_PATH}" &&  -d ${MLNX_OFED_PATH} ]]; then
    cp -r ${MLNX_OFED_PATH}/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS/ofed-scripts*src.rpm    $baseoutpath/mellanox/SRPMS
    cp -r ${MLNX_OFED_PATH}/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS/rdma-core*src.rpm       $baseoutpath/mellanox/SRPMS
    cp -r ${MLNX_OFED_PATH}/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS/mlnx-iproute2*src.rpm   $baseoutpath/mellanox/SRPMS
    cp -r ${MLNX_OFED_PATH}/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS/mlnx-ofa_kernel*src.rpm $baseoutpath/mellanox/SRPMS
    cp -r ${MLNX_OFED_PATH}/MLNX_OFED_SRC-${MLNX_OFED_VERSION}/SRPMS/kernel-mft*src.rpm      $baseoutpath/mellanox/SRPMS
  elif [[ ! -z "${OFED_SRC_PATH}" && -d ${OFED_SRC_PATH} ]]; then
    cp -r ${OFED_SRC_PATH}/SRPMS/ofed-scripts*src.rpm    $baseoutpath/mellanox/SRPMS
    cp -r ${OFED_SRC_PATH}/SRPMS/rdma-core*src.rpm       $baseoutpath/mellanox/SRPMS
    cp -r ${OFED_SRC_PATH}/SRPMS/mlnx-iproute2*src.rpm   $baseoutpath/mellanox/SRPMS
    cp -r ${OFED_SRC_PATH}/SRPMS/mlnx-ofa_kernel*src.rpm $baseoutpath/mellanox/SRPMS
    cp -r ${OFED_SRC_PATH}/SRPMS/kernel-mft*src.rpm      $baseoutpath/mellanox/SRPMS
  fi
  if [[ ! -z "${MFT_PATH}" && -d ${MFT_PATH} ]]; then
    cp -r ${MFT_PATH}/mft*${MFT_VERSION}.arm64.rpm $baseoutpath/mellanox/RPMS
  fi
}

fakeroot update_rootfs_bluefield() {
  if [ ! -z "${MLNX_BLUEFIELD_FW_PATH}" ]; then
    if [ -d ${MLNX_BLUEFIELD_FW_PATH} ]; then
      install -d ${IMAGE_ROOTFS}/lib/firmware/mellanox
      cp ${MLNX_BLUEFIELD_FW_PATH}/* ${IMAGE_ROOTFS}/lib/firmware/mellanox
    fi
  fi
}

ROOTFS_POSTPROCESS_COMMAND += " update_rootfs_bluefield; "

fakeroot do_populate_update_src_full () {
         # /etc/resolv.conf is a very popular file and numerous
         # packages compete for it.  We want systemd-resolved to
         # control it.  You may change this behavior by modifying
         # this function.  Note that /etc/resolv.conf is currently
         # controlled/created during boot by
         # /usr/lib/tmpfiles.d/etc.conf.

         # connman installs this file unconditionally assuming
         # a root file system may be read only so we need to
         # remove it since if /etc/resolv.conf exists, systemd
         # will not override it.

         rm -f ${IMAGE_ROOTFS}/etc/resolv.conf

         # micro DHCP client used by busybox
         rm -f ${IMAGE_ROOTFS}/etc/udhcpc.d/50default

         rm -f ${IMAGE_ROOTFS}/etc/tmpfiles.d/connman_resolvconf.conf

         # If you want to override /usr/lib/tmpfiles.d/etc.conf
         # then add your own etc.conf in /etc/tmpfiles.d which
         # has a higher priority.

         rm -f ${IMAGE_ROOTFS}/etc/tmpfiles.d/etc.conf
}

IMAGE_PREPROCESS_COMMAND += "do_populate_update_src_full; "
