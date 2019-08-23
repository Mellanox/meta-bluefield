#!/bin/sh

# Copyright (c) 2018, Mellanox Technologies
# All rights reserved.
#
# Redistribution and use in source and binary forms, with or without
# modification, are permitted provided that the following conditions are met:
#
# 1. Redistributions of source code must retain the above copyright notice, this
#    list of conditions and the following disclaimer.
# 2. Redistributions in binary form must reproduce the above copyright notice,
#    this list of conditions and the following disclaimer in the documentation
#    and/or other materials provided with the distribution.
#
# THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
# ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
# WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
# DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
# ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
# (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
# LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
# ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
# (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
# SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
# The views and conclusions contained in the software and documentation are those
# of the authors and should not be interpreted as representing official policies,
# either expressed or implied, of the FreeBSD Project.

# preinit
#
# Do some pre-initialization before running /sbin/init

# Print a message to the printk buffer
kmsg(){
	echo "$@" > /dev/kmsg
}

kmsg "start bluefield preinit"

kmsg "mount persistent data partition"
mount /dev/mmcblk0p8 /data

# The reason we do these mounts here
# is to preempt systemd. If these mounts are performed
# by systemd, any units created by the user in /etc/systemd/system
# will be picked up, but NOT executed. We get around this issue
# by mounting /etc before systemd ever starts.
if [ -f /data/persistconfig ]; then
	kmsg "mount etc partition"

	mkdir -p /etc.lower
	mount /etc.img /etc.lower -o ro
	mount -t overlay -o lowerdir=/etc.lower,upperdir=/data/etc,workdir=/data/.etc.work none /etc
fi

kmsg "start /sbin/init ($(readlink -f /sbin/init))"
exec /sbin/init
